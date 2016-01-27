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
import com.speedment.internal.core.runtime.DefaultSpeedmentApplicationLifecycle;
import com.speedment.internal.core.runtime.SpeedmentApplicationLifecycle;
import java.io.File;
import java.util.function.Supplier;
import org.apache.maven.plugin.logging.Log;
import com.speedment.component.ComponentConstructor;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil
 */
final class SpeedmentInitializer {
    
    private final Log log;
    private final File configFile;
    private final Supplier<ComponentConstructor<?>[]> componentBuilders;
    
    public SpeedmentInitializer(Log log, File configFile, Supplier<ComponentConstructor<?>[]> componentBuilders) {
        this.log               = requireNonNull(log);
        this.configFile        = configFile; // Can be null.
        this.componentBuilders = requireNonNull(componentBuilders);
    }
    
    public Speedment build() {
        final SpeedmentApplicationLifecycle<?> lifecycle = new DefaultSpeedmentApplicationLifecycle(configFile);
        final ComponentConstructor<?>[] constructors = componentBuilders.get();
        
        if (constructors != null) {
            for (final ComponentConstructor<?> constructor : constructors) {
                if (constructor != null) {
                    lifecycle.with(constructor);
                } else {
                    log.warn("Specified ComponentConstructor is null.");
                }
            }
        } else {
            log.info("Component constructors container is not defined.");
        }

        return lifecycle.build();
    }
}