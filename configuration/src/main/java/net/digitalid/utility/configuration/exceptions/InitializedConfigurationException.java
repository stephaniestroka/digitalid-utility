package net.digitalid.utility.configuration.exceptions;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.contracts.Require;

/**
 * This exception indicates that a configuration has already been initialized.
 */
public class InitializedConfigurationException extends InitializationException {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    private final Configuration<?> configuration;
    
    /**
     * Returns the configuration that has already been initialized.
     * 
     * @ensure result != null : "The returned configuration may not be null.";
     */
    public Configuration<?> getConfiguration() {
        return configuration;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected InitializedConfigurationException(Configuration<?> configuration) {
        super("A configuration was already initialized.");
        
        Require.that(configuration != null).orThrow("The configuration may not be null.");
        
        this.configuration = configuration;
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
