package net.digitalid.utility.testing;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.filter.LevelBasedLoggingFilter;
import net.digitalid.utility.logging.filter.LoggingFilter;
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
     * Sets the output file of the logger.
     */
    @Impure
    @BeforeClass
    public static void initializeLogger() {
        Logger.logger.set(FileLogger.with("target/test-logs/test.log"));
        LoggingFilter.filter.set(LevelBasedLoggingFilter.with(Level.VERBOSE)); // TODO: Chose a configuration-based filter instead.
    }
    
}
