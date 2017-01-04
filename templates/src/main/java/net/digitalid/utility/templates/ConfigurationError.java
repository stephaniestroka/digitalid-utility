package net.digitalid.utility.templates;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.errors.InitializationError;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A configuration error indicates that a configuration has not been initialized with a provider.
 */
@Immutable
@GenerateBuilder
@GenerateSubclass
public abstract class ConfigurationError extends InitializationError {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    /**
     * Returns the configuration which has not been initialized with a provider.
     */
    @Pure
    public abstract @Nonnull Configuration<?> getConfiguration();
    
    /* -------------------------------------------------- Message -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String getMessage() {
        return Strings.format("The configuration $ has not been initialized with a provider.", getConfiguration());
    }
    
}
