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

import com.speedment.core.code.model.java.MainGenerator;
import com.speedment.core.config.model.Project;
import com.speedment.core.config.model.impl.utils.GroovyParser;
import com.speedment.gui.MainApp;
import java.io.IOException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import static javafx.application.Application.launch;

/**
 *
 * @author Emil Forslund
 * @goal generate
 */
public class GUIMojo extends AbstractMojo {

    /**
     * @parameter expression="${generate.groovyFile}"
     */
    private String groovyFile;
    
    /**
     * @parameter default-value=true
     */
    private boolean showGUI;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        getLog().info("Starting Speedment");
        
        if (showGUI) {
            getLog().info("Running Speedment GUI");
            launch(MainApp.class);
        } else {
            if (groovyFile == null) {
                getLog().error("If you want to start Speedment without GUI, you must configure a .groovy file using the <groovyFile> tag.");
            } else {
                getLog().info("Creating from groovy file: '" + groovyFile + "'.");
            
                try {
                    final Project p = GroovyParser.projectFromGroovy(groovyFile);
                    new MainGenerator().accept(p);
                } catch (IOException ex) {
                    getLog().error("IOException casted when parsing Groovy-file.");
                }
            }
        }
    }
}