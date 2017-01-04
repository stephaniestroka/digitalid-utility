package net.digitalid.utility.exceptions;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.throwable.CustomThrowable;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * An unchecked exception allows the programmer to avoid throwing a checked exception.
 * It is often used when an operation that always succeeds under normal circumstances fails.
 */
@Immutable
public abstract class UncheckedException extends RuntimeException implements CustomThrowable {
    
    /* -------------------------------------------------- Cause -------------------------------------------------- */
    
    @Pure
    @Override
    public abstract @Nonnull Exception getCause();
    
    /* -------------------------------------------------- Message -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String getMessage() {
        return "This exception wraps a " + getCause().getClass().getSimpleName() + ".";
    }
    
}
