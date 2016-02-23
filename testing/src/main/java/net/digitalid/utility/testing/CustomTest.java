package net.digitalid.utility.testing;

import net.digitalid.utility.configuration.Configuration;

import org.junit.BeforeClass;

/**
 * The base class for all unit tests written for Digital ID.
 */
public abstract class CustomTest extends LoggerSetup {
    
    /**
     * Initializes the library.
     */
    @BeforeClass
    public static void initializeLibrary() {
        Configuration.initializeAllConfigurations();
    }
    
}
