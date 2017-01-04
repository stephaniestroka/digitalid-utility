package net.digitalid.utility.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.throwable.CustomThrowable;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * An internal exception indicates a condition that should never occur in a correct program.
 * A server might still want to catch internal exceptions in order to continue its operation.
 * 
 * @see CaseException
 */
@Immutable
public abstract class InternalException extends RuntimeException implements CustomThrowable {
    
    /* -------------------------------------------------- Message -------------------------------------------------- */
    
    @Pure
    @Override
    public abstract @Nonnull String getMessage();
    
    /* -------------------------------------------------- Cause -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nullable Throwable getCause() {
        return null;
    }
    
}
