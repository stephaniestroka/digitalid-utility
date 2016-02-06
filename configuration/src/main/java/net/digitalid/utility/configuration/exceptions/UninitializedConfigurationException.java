package net.digitalid.utility.configuration.exceptions;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.contracts.Require;

/**
 * This exception indicates that a configuration was not initialized.
 */
public class UninitializedConfigurationException extends InitializationException {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    private final Configuration<?> configuration;
    
    /**
     * Returns the configuration that was not initialized.
     * 
     * @ensure result != null : "The returned configuration may not be null.";
     */
    public Configuration<?> getConfiguration() {
        return configuration;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected UninitializedConfigurationException(Configuration<?> configuration) {
        super("A configuration was not initialized.");
        
        Require.that(configuration != null).orThrow("The configuration may not be null.");
        
        this.configuration = configuration;
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
