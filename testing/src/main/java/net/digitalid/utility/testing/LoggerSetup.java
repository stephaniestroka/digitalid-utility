package net.digitalid.utility.testing;

import java.io.File;
import java.io.FileNotFoundException;

import net.digitalid.utility.configuration.InitializationError;
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
    public static void setUpLogging() throws FileNotFoundException {
        final File directory = new File("target" + File.separator + "test-logs");
        if (!directory.exists() && !directory.mkdirs()) { throw InitializationError.of("Could not create the directory '" + directory.getPath() + "'."); }
        final File file = new File(directory.getPath() + File.separator + "test.log");
        Logger.configuration.set(FileLogger.of(file));
        Level.configuration.set(Level.VERBOSE);
    }
    
}
