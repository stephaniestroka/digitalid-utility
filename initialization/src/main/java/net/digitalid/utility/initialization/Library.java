package net.digitalid.utility.initialization;

import java.util.ServiceLoader;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.configuration.Initializer;
import net.digitalid.utility.configuration.exceptions.MaskingInitializationException;
import net.digitalid.utility.validation.annotations.type.Utiliy;

/**
 * Description.
 */
@Utiliy
public class Library {
    
    /**
     * Initializes all configurations of this library.
     * 
     * @throws InitializationError if an issue occurs.
     */
    public static void initialize() {
        for (Initializer initializer : ServiceLoader.load(Initializer.class)) {
            System.out.println(initializer.getClass() + " was loaded."); // TODO: Log instead!
        }
        try {
            for (Configuration<?> configuration : Configuration.getAllConfigurations()) {
                configuration.initialize();
            }
        } catch (Exception exception) {
            throw MaskingInitializationException.with(exception);
        }
    }
    
}
