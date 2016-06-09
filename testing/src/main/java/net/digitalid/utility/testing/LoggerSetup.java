package net.digitalid.utility.testing;

import java.io.File;
import java.io.FileNotFoundException;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.file.Files;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.exceptions.InvalidConfigurationException;
import net.digitalid.utility.logging.filter.ConfigurationBasedLoggingFilter;
import net.digitalid.utility.logging.filter.LoggingFilter;
import net.digitalid.utility.logging.filter.LoggingRule;
import net.digitalid.utility.logging.logger.FileLogger;
import net.digitalid.utility.logging.logger.Logger;
import net.digitalid.utility.validation.annotations.type.Stateless;

import org.junit.Assert;
import org.junit.BeforeClass;

/**
 * This class sets the output file of the logger.
 */
@Stateless
public abstract class LoggerSetup extends Assert {
    
    /**
     * Initializes the output file of the logger.
     */
    @Impure
    @BeforeClass
    public static void initializeLogger() throws InvalidConfigurationException, FileNotFoundException {
        LoggingFilter.filter.set(ConfigurationBasedLoggingFilter.with(Files.with("config/TestingLogging.conf"), LoggingRule.with(Level.VERBOSE, "net.digitalid." + new File(".").getAbsoluteFile().getParentFile().getName()), LoggingRule.with(Level.INFORMATION)));
        Logger.logger.set(FileLogger.with(Files.with("target/test-logs/test.log")));
    }
    
}
