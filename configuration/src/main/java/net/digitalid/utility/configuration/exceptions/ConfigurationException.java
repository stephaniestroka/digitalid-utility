package net.digitalid.utility.configuration.exceptions;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception indicates that a configuration caused a problem.
 * 
 * @see InitializedConfigurationException
 * @see UninitializedConfigurationException
 */
@Immutable
public class ConfigurationException extends InitializationException {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    private final @Nonnull Configuration<?> configuration;
    
    /**
     * Returns the configuration that caused a problem.
     */
    @Pure
    public @Nonnull Configuration<?> getConfiguration() {
        return configuration;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ConfigurationException(@Nonnull String message, @Nonnull Configuration<?> configuration) {
        super(message, configuration);
        
        Require.that(configuration != null).orThrow("The configuration may not be null.");
        
        this.configuration = configuration;
    }
    
}
