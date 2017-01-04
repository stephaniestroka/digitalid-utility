package net.digitalid.utility.configuration.errors;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.errors.InitializationError;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A dependency error indicates that the configurations have cyclic dependencies.
 */
@Immutable
public abstract class DependencyError extends InitializationError {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    /**
     * Returns the configuration to which the dependency should have been added.
     */
    @Pure
    public abstract @Nonnull Configuration<?> getConfiguration();
    
    /* -------------------------------------------------- Dependency -------------------------------------------------- */
    
    /**
     * Returns the dependency which should have been added to the configuration.
     */
    @Pure
    public abstract @Nonnull Configuration<?> getDependency();
    
    /* -------------------------------------------------- Message -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String getMessage() {
        return Strings.format("Could not add $ as a dependency of $, as $ already depends on $.", getDependency(), getConfiguration(), getDependency(), getConfiguration(), getDependency().getDependencyChainAsString(getConfiguration()));
    }
    
}
