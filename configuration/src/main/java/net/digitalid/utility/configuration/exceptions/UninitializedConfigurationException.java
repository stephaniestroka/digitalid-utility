package net.digitalid.utility.configuration.exceptions;

import net.digitalid.utility.configuration.Configuration;

/**
 * This exception indicates that a configuration was not initialized.
 */
public class UninitializedConfigurationException extends ConfigurationException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected UninitializedConfigurationException(Configuration<?> configuration) {
        super("The configuration $ was not initialized.", configuration);
    }
    
    /**
     * Returns an uninitialized configuration exception with the given configuration.
     * 
     * @param configuration the configuration that was not initialized.
     * 
     * @require configuration != null : "The configuration may not be null.";
     */
    public static UninitializedConfigurationException with(Configuration<?> configuration) {
        return new UninitializedConfigurationException(configuration);
    }
    
}
