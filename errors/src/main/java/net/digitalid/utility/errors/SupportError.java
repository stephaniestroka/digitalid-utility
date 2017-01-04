package net.digitalid.utility.errors;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This error indicates that the runtime system does not support a required feature.
 */
@Immutable
public abstract class SupportError extends InitializationError {
    
    /* -------------------------------------------------- Cause -------------------------------------------------- */
    
    @Pure
    @Override
    public abstract @Nonnull Throwable getCause();
    
}
