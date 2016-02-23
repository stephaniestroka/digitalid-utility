package net.digitalid.utility.configuration.exceptions;

import net.digitalid.utility.configuration.Configuration;

/**
 * This exception indicates that a configuration has already been initialized.
 */
public class InitializedConfigurationException extends InitializationException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected InitializedConfigurationException(Configuration<?> configuration) {
        super("The configuration $ was already initialized.", configuration);
    }
    
    /**
     * Returns an initialized configuration exception with the given configuration.
     * 
     * @param configuration the configuration that has already been initialized.
     * 
     * @require configuration != null : "The configuration may not be null.";
     */
    public static InitializedConfigurationException with(Configuration<?> configuration) {
        return new InitializedConfigurationException(configuration);
    }
    
}
