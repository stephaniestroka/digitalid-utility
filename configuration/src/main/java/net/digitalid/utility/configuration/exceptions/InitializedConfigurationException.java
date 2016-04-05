package net.digitalid.utility.configuration.exceptions;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception indicates that a configuration has already been initialized.
 */
@Immutable
public class InitializedConfigurationException extends ConfigurationException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected InitializedConfigurationException(@Nonnull Configuration<?> configuration) {
        super("The configuration $ was already initialized.", configuration);
    }
    
    /**
     * Returns an initialized configuration exception with the given configuration.
     * 
     * @param configuration the configuration that has already been initialized.
     */
    @Pure
    public static @Nonnull InitializedConfigurationException with(@Nonnull Configuration<?> configuration) {
        return new InitializedConfigurationException(configuration);
    }
    
}
