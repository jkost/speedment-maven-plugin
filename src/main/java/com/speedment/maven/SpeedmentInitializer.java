/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.maven;

import com.speedment.Speedment;
import com.speedment.component.Component;
import com.speedment.component.ComponentBuilder;
import com.speedment.internal.core.platform.SpeedmentFactory;
import java.util.function.Supplier;
import org.apache.maven.plugin.logging.Log;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil
 */
final class SpeedmentInitializer {
    
    private final Log log;
    private final Supplier<ComponentBuilder[]> componentBuilders;
    
    public SpeedmentInitializer(Log log, Supplier<ComponentBuilder[]> componentBuilders) {
        this.log               = requireNonNull(log);
        this.componentBuilders = requireNonNull(componentBuilders);
    }
    
    public Speedment build() {
        final Speedment speedment = SpeedmentFactory.newSpeedmentInstance();
        final ComponentBuilder[] builders = componentBuilders.get();
        
        if (builders != null) {
            for (final ComponentBuilder<?> builder : builders) {
                if (builder != null) {
                    buildAndPutComponent(speedment, builder);
                } else {
                    log.warn("Specified ComponentBuilder is null.");
                }
            }
        } else {
            log.info("Component container is not defined.");
        }
        
        speedment.components().forEach(Component::resolve);
        speedment.components().forEach(Component::start);

        return speedment;
    }
        
    private <C extends Component> void buildAndPutComponent(Speedment speedment, ComponentBuilder<C> builder) {
        final C comp = builder.withSpeedment(speedment).build();
        log.info("Loading component '" + comp.getComponentClass().getSimpleName() + "'.");
        speedment.put(comp.initialize());
    }
}