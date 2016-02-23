package net.digitalid.utility.configuration.exceptions;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.contracts.Require;

/**
 * This exception indicates that a configuration caused a problem.
 */
public class ConfigurationException extends InitializationException {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    private final Configuration<?> configuration;
    
    /**
     * Returns the configuration that caused a problem.
     * 
     * @ensure result != null : "The returned configuration may not be null.";
     */
    public Configuration<?> getConfiguration() {
        return configuration;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ConfigurationException(String message, Configuration<?> configuration) {
        super(message, configuration);
        
        Require.that(configuration != null).orThrow("The configuration may not be null.");
        
        this.configuration = configuration;
    }
    
}
