package net.digitalid.utility.testing;

import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.logger.FileLogger;
import net.digitalid.utility.logging.logger.Logger;

import org.junit.BeforeClass;

/**
 * This class sets the output file of the logger.
 */
public class LoggerSetup extends TestingBase {
    
    /**
     * Sets the output file of the logger.
     */
    @BeforeClass
    public static void setUpLogging() {
        Logger.configuration.set(FileLogger.of("target/test-logs/test.log"));
        Level.configuration.set(Level.VERBOSE);
    }
    
}
