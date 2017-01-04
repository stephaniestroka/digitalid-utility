package net.digitalid.utility.configuration.errors;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.configuration.Initializer;
import net.digitalid.utility.errors.InitializationError;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * An initializer error wraps a throwable thrown by an {@link Initializer}.
 */
@Immutable
public abstract class InitializerError extends InitializationError {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    /**
     * Returns the target configuration of the failed initializer.
     */
    @Pure
    public abstract @Nonnull Configuration<?> getConfiguration();
    
    /* -------------------------------------------------- Initializer -------------------------------------------------- */
    
    /**
     * Returns the initializer that ran into a problem.
     */
    @Pure
    public abstract @Nonnull Initializer getInitializer();
    
    /* -------------------------------------------------- Cause -------------------------------------------------- */
    
    @Pure
    @Override
    public abstract @Nonnull Throwable getCause();
    
    /* -------------------------------------------------- Message -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String getMessage() {
        return Strings.format("The initializer $ of the configuration $ threw " + Strings.prependWithIndefiniteArticle(getCause().getClass().getSimpleName()) + ".", getInitializer().getClass().getSimpleName(), getConfiguration());
    }
    
}
