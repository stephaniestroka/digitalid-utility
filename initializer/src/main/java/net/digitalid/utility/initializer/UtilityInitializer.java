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
package net.digitalid.utility.initializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ConcurrentModificationException;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.method.PureWithSideEffects;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.file.Files;
import net.digitalid.utility.initialization.annotations.Initialize;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.logging.filter.ConfigurationBasedLoggingFilter;
import net.digitalid.utility.logging.filter.LevelBasedLoggingFilter;
import net.digitalid.utility.logging.filter.LoggingFilter;
import net.digitalid.utility.logging.filter.LoggingRule;
import net.digitalid.utility.logging.logger.Logger;
import net.digitalid.utility.logging.logger.RotatingFileLogger;
import net.digitalid.utility.logging.logger.StandardOutputLogger;
import net.digitalid.utility.threading.Threading;
import net.digitalid.utility.validation.annotations.file.existence.ExistentParent;
import net.digitalid.utility.validation.annotations.file.path.Absolute;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class initializes the utility classes.
 */
@Utility
public abstract class UtilityInitializer {
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    /**
     * Stores a dummy configuration in order to have an initialization target.
     */
    public static final @Nonnull Configuration<Boolean> configuration = Configuration.with(Boolean.TRUE);
    
    /**
     * Initializes the main thread as early as possible.
     */
    @PureWithSideEffects
    @Initialize(target = UtilityInitializer.class)
    public static void initializeMainThread() throws FileNotFoundException {
        Thread.currentThread().setName("Main");
    }
    
    /**
     * Initializes the default uncaught exception handler as early as possible.
     */
    @PureWithSideEffects
    @Initialize(target = UtilityInitializer.class)
    public static void initializeDefaultUncaughtExceptionHandler() throws FileNotFoundException {
        // NetBeans 8.1 crashes if you use type annotations on anonymous classes and lambda expressions!
        Thread.setDefaultUncaughtExceptionHandler((Thread thread, Throwable throwable) -> Log.fatal("The following problem caused this thread to terminate.", throwable));
    }
    
    /**
     * Initializes the configuration directory with '~/.digitalid/'.
     */
    @PureWithSideEffects
    @Initialize(target = Files.class, dependencies = UtilityInitializer.class)
    public static void initializeDirectory() throws IOException {
        if (!Files.directory.isSet()) {
            final @Nonnull @Absolute @ExistentParent File directory = Files.relativeToWorkingDirectory(System.getProperty("user.home") + "/.digitalid/");
            if (directory.isDirectory() || directory.mkdir()) {
                Files.directory.set(directory);
                Log.verbose("Set the configuration directory to '~/.digitalid/'.");
            } else {
                throw new IOException("Could not create the directory '~/.digitalid/'.");
            }
        } else {
            Log.verbose("Did not set the configuration directory to '~/.digitalid/' because it is already set to $.", Files.directory.get().getAbsolutePath());
        }
    }
    
    /**
     * Initializes the logging filter with a configuration-based logging filter.
     * 
     * @throws IllegalArgumentException if a rule has an invalid level.
     */
    @PureWithSideEffects
    @Initialize(target = LoggingFilter.class, dependencies = Files.class)
    public static void initializeLoggingFilter() throws IllegalArgumentException {
        if (LoggingFilter.filter.get() instanceof LevelBasedLoggingFilter) {
            LoggingFilter.filter.set(ConfigurationBasedLoggingFilter.with(Files.relativeToConfigurationDirectory("configs/logging.conf"), LoggingRule.with(Level.INFORMATION)));
            Log.verbose("Replaced the default level-based logging filter with a configuration-based logging filter.");
        } else {
            Log.verbose("Did not replace the non-default logging filter with a configuration-based logging filter.");
        }
    }
    
    /**
     * Initializes the logger with a rotating file logger.
     */
    @PureWithSideEffects
    @Initialize(target = Logger.class)
    public static void initializeLogger() throws FileNotFoundException {
        if (Logger.logger.get() instanceof StandardOutputLogger) {
            Logger.logger.set(RotatingFileLogger.withDefaultDirectory());
            Log.verbose("Replaced the default standard output logger with a rotating file logger.");
        } else {
            Log.verbose("Did not replace the non-default logger with a rotating file logger.");
        }
    }
    
    /* -------------------------------------------------- Threading -------------------------------------------------- */
    
    /**
     * Ensures that the threading configuration is loaded before the configurations are initialized.
     * This initialization was necessary to prevent a {@link ConcurrentModificationException} in {@link Configuration#initializeAllConfigurations()}.
     */
    @Pure
    @Initialize(target = Threading.class)
    public static void initializeThreading() {}
    
}
