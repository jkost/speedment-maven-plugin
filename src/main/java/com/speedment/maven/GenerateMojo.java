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
import com.speedment.config.Project;
import com.speedment.internal.core.code.MainGenerator;
import com.speedment.internal.core.config.utils.GroovyParser;
import static com.speedment.internal.ui.UISession.DEFAULT_GROOVY_LOCATION;
import java.io.File;
import java.io.IOException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;


/**
 *
 * @author Emil Forslund
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public final class GenerateMojo extends AbstractSpeedmentMojo {

    @Parameter
    private ComponentBuilder<?>[] components;

    @Parameter(defaultValue = DEFAULT_GROOVY_LOCATION)
    private File groovyFile;

    @Override
    public void execute(Speedment speedment) throws MojoExecutionException, MojoFailureException {
        getLog().info("Creating from groovy file: '" + groovyFile.getAbsolutePath() + "'.");
        
        if (hasGroovyFile()) {
            try {
                final Project p = GroovyParser.projectFromGroovy(speedment, groovyFile.toPath());
                new MainGenerator(speedment).accept(p);
            } catch (IOException ex) {
                final String err = "IOException thrown when parsing Groovy-file.";
                getLog().error(err);
                throw new MojoExecutionException(err, ex);
            }
        } else {
            final String err = "To run speedment:generate a valid .groovy-file need to be specified.";
            getLog().error(err);
            throw new MojoExecutionException(err);
        }
    }

    @Override
    protected ComponentBuilder<?>[] components() {
        return components;
    }
    
    @Override
    protected File groovyLocation() {
        return groovyFile;
    }

    @Override
    protected String launchMessage() {
        return "Starting Speedment";
    }
}