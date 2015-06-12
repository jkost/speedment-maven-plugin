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
import java.io.File;
import java.io.IOException;
import org.apache.maven.plugin.AbstractMojo;
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
public class GenerateMojo extends AbstractMojo {

    @Parameter(defaultValue = "groovy/speedment.groovy")
    private File groovyFile;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Starting Speedment");

        if (groovyFile == null) {
            final String err = "If you want to use speedment:generate, you must configure a .groovy file using the <groovyFile> tag.";
            getLog().error(err);
            throw new MojoExecutionException(err);
        } else if (!groovyFile.exists()) {
            final String err = "The specified groovy-file '" + groovyFile.getAbsolutePath() + "' does not exist.";
            getLog().error(err);
            throw new MojoExecutionException(err);
        } else if (!groovyFile.canRead()) {
            final String err = "The specified groovy-file '" + groovyFile.getAbsolutePath() + "' is not readable.";
            getLog().error(err);
            throw new MojoExecutionException(err);
        } else {
            getLog().info("Creating from groovy file: '" + groovyFile.getAbsolutePath() + "'.");

            try {
                final Project p = GroovyParser.projectFromGroovy(groovyFile.toPath());
                new MainGenerator().accept(p);
            } catch (IOException ex) {
                final String err = "IOException casted when parsing Groovy-file.";
                getLog().error(err);
                throw new MojoExecutionException(err, ex);
            }
        }
    }
}
