package net.digitalid.utility.testing;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.validation.annotations.type.Stateless;

import org.junit.BeforeClass;

/**
 * The base class for all unit tests written for Digital ID.
 */
@Stateless
public abstract class CustomTest extends LoggerSetup {
    
    /**
     * Initializes the library.
     */
    @Impure
    @BeforeClass
    public static void initializeLibrary() {
        Configuration.initializeAllConfigurations();
    }
    
}
