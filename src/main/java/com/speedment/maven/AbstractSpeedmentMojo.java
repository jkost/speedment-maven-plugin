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
import com.speedment.internal.core.platform.SpeedmentFactory;
import com.speedment.internal.core.platform.component.Component;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 *
 * @author Emil Forslund
 */
abstract class AbstractSpeedmentMojo extends AbstractMojo {
    
    private final Speedment speedment;
    
    protected abstract Component[] components();
    protected abstract String launchMessage();
    
    protected AbstractSpeedmentMojo() {
        this.speedment = SpeedmentFactory.newSpeedmentInstance();
    }
    
    /**
     * @return the speedment
     */
    protected Speedment getSpeedment() {
        return speedment;
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info(launchMessage());
        
        if (components() != null) {
            for (final Component comp : components()) {
                getLog().info("Loading component '" + comp.getComponentClass().getSimpleName() + "'.");
                getSpeedment().add(comp);
            }
        } else {
            getLog().info("Component container is not defined.");
        }
    }
}