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
import com.speedment.component.ComponentBuilder;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 *
 * @author Emil Forslund
 */
abstract class AbstractSpeedmentMojo extends AbstractMojo {
    
    private final SpeedmentInitializer lifecycle;

    protected abstract ComponentBuilder[] components();
    protected abstract String launchMessage();
    protected abstract void execute(Speedment speedment) throws MojoExecutionException, MojoFailureException;
    
    protected AbstractSpeedmentMojo() {
        lifecycle = new SpeedmentInitializer(getLog(), this::components);
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info(launchMessage());
        execute(lifecycle.build());
    }
}