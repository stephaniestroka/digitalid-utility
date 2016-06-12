package net.digitalid.utility.initializer;

import java.io.FileNotFoundException;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.file.Files;
import net.digitalid.utility.initialization.annotations.Initialize;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.logging.exceptions.InvalidConfigurationException;
import net.digitalid.utility.logging.filter.ConfigurationBasedLoggingFilter;
import net.digitalid.utility.logging.filter.LevelBasedLoggingFilter;
import net.digitalid.utility.logging.filter.LoggingFilter;
import net.digitalid.utility.logging.filter.LoggingRule;
import net.digitalid.utility.logging.logger.Logger;
import net.digitalid.utility.logging.logger.RotatingFileLogger;
import net.digitalid.utility.logging.logger.StandardOutputLogger;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class initializes the utility classes.
 */
@Utility
public class UtilityInitializer {
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    /**
     * Stores a dummy configuration in order to have an initialization target.
     */
    public static final @Nonnull Configuration<String> configuration = Configuration.with("");
    
    /**
     * Initializes the default uncaught exception handler as early as possible.
     */
    @Impure
    @Initialize(target = UtilityInitializer.class)
    public static void initializeDefaultUncaughtExceptionHandler() throws FileNotFoundException {
        // NetBeans 8.1 crashes if you use type annotations on anonymous classes and lambda expressions!
        Thread.setDefaultUncaughtExceptionHandler((Thread thread, Throwable throwable) -> Log.error("The following issue caused this thread to terminate.", throwable));
    }
    
    /**
     * Initializes the configuration directory with '~/.digitalid/'.
     */
    @Impure
    @Initialize(target = Files.class, dependencies = {UtilityInitializer.class})
    public static void initializeDirectory() {
        if (!Files.directory.isSet()) {
            Files.directory.set(Files.relativeToWorkingDirectory(System.getProperty("user.home") + "/.digitalid/"));
            Log.verbose("Set the configuration directory to '~/.digitalid/'.");
        } else {
            Log.verbose("Did not set the configuration directory to '~/.digitalid/' because it is already set to $.", Files.directory.get().getAbsolutePath());
        }
    }
    
    /**
     * Initializes the logging filter with a configuration-based logging filter.
     */
    @Impure
    @Initialize(target = LoggingFilter.class, dependencies = {Files.class})
    public static void initializeLoggingFilter() throws InvalidConfigurationException {
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
    @Impure
    @Initialize(target = Logger.class, dependencies = {Files.class})
    public static void initializeLogger() throws FileNotFoundException {
        if (Logger.logger.get() instanceof StandardOutputLogger) {
            Logger.logger.set(RotatingFileLogger.withDefaultDirectory());
            Log.verbose("Replaced the default standard output logger with a rotating file logger.");
        } else {
            Log.verbose("Did not replace the non-default logger with a rotating file logger.");
        }
    }
    
}
