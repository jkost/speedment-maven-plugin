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


import com.speedment.core.platform.component.Component;
import com.speedment.gui.MainApp;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import static javafx.application.Application.launch;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 *
 * @author Emil Forslund
 */
@Mojo(name = "gui", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class GUIMojo extends AbstractSpeedmentMojo {
    
    @Parameter
    private Component[] components;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        super.execute();
        launch(MainApp.class);
    }

    @Override
    protected Component[] components() {
        return components;
    }

    @Override
    protected String launchMessage() {
        return "Running Speedment GUI";
    }
}
