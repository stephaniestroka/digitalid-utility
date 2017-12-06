/*
 * Copyright (C) 2017 Synacts GmbH, Switzerland (info@synacts.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.digitalid.utility.testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.PureWithSideEffects;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.file.Files;
import net.digitalid.utility.initialization.annotations.Initialize;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.filter.ConfigurationBasedLoggingFilter;
import net.digitalid.utility.logging.filter.LoggingFilter;
import net.digitalid.utility.logging.filter.LoggingRule;
import net.digitalid.utility.logging.logger.FileLogger;
import net.digitalid.utility.logging.logger.Logger;
import net.digitalid.utility.validation.annotations.file.existence.ExistentParent;
import net.digitalid.utility.validation.annotations.file.path.Absolute;
import net.digitalid.utility.validation.annotations.type.Stateless;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;

/**
 * The base class for all unit tests written for Digital ID.
 */
@Stateless
public abstract class UtilityTest extends Assertions {
    
    /* -------------------------------------------------- Directory -------------------------------------------------- */
    
    /**
     * Initializes the directory.
     */
    @PureWithSideEffects
    @Initialize(target = Files.class)
    public static void initializeDirectory() throws IOException {
        final @Nonnull @Absolute @ExistentParent File directory = Files.relativeToWorkingDirectory("target/test-files/");
        if (directory.isDirectory() || directory.mkdir()) { Files.directory.set(directory); }
        else { throw new IOException("Could not create the directory 'target/test-files/'."); }
    }
    
    /* -------------------------------------------------- Logging Filter -------------------------------------------------- */
    
    /**
     * Initializes the logging filter.
     */
    @PureWithSideEffects
    @Initialize(target = LoggingFilter.class)
    public static void initializeLoggingFilter() {
        final @Nonnull @Absolute File projectDirectory = new File("").getAbsoluteFile();
        final @Nonnull String callerPrefix = "net.digitalid." + projectDirectory.getParentFile().getName() + "." + projectDirectory.getName();
        LoggingFilter.filter.set(ConfigurationBasedLoggingFilter.with(Files.relativeToWorkingDirectory("config/TestingLogging.conf"), LoggingRule.with(Level.VERBOSE, callerPrefix + "."), LoggingRule.with(Level.INFORMATION)));
    }
    
    /* -------------------------------------------------- File Logger -------------------------------------------------- */
    
    /**
     * Initializes the output file of the logger.
     */
    @PureWithSideEffects
    @Initialize(target = Logger.class)
    public static void initializeLogger() throws FileNotFoundException {
        Logger.logger.set(FileLogger.with(Files.relativeToWorkingDirectory("target/test-logs/test.log")));
    }
    
    /* -------------------------------------------------- All Configurations -------------------------------------------------- */
    
    private static boolean initialized = false;
    
    /**
     * Initializes all configurations of the library.
     */
    @BeforeClass
    @PureWithSideEffects
    public static void initializeAllConfigurations() {
        if (!initialized) {
            initialized = true;
            Configuration.initializeAllConfigurations();
        }
    }
    
}
