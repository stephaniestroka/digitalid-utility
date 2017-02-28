package net.digitalid.utility.testing;

import java.io.File;
import java.io.FileNotFoundException;

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
import net.digitalid.utility.validation.annotations.file.path.Absolute;
import net.digitalid.utility.validation.annotations.type.Stateless;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;

/**
 * The base class for all unit tests written for Digital ID.
 */
@Stateless
public abstract class UtilityTest extends Assertions {
    
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
