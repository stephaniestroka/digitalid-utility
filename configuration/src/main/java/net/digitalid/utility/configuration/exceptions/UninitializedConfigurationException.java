package net.digitalid.utility.configuration.exceptions;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception indicates that a configuration was not initialized.
 */
@Immutable
public class UninitializedConfigurationException extends ConfigurationException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected UninitializedConfigurationException(@Nonnull Configuration<?> configuration) {
        super("The configuration $ was not initialized.", configuration);
    }
    
    /**
     * Returns an uninitialized configuration exception with the given configuration.
     * 
     * @param configuration the configuration that was not initialized.
     */
    @Pure
    public static @Nonnull UninitializedConfigurationException with(@Nonnull Configuration<?> configuration) {
        return new UninitializedConfigurationException(configuration);
    }
    
}
