package net.digitalid.utility.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception indicates that an operation which should always succeed under normal circumstances failed.
 */
@Immutable
public class UnexpectedFailureException extends InternalException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected UnexpectedFailureException(@Nullable String message, @Nullable Exception cause, @Nullable Object... arguments) {
        super(message, cause, arguments);
    }
    
    /**
     * Returns an unexpected failure exception with the given message and cause.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Pure
    public static @Nonnull UnexpectedFailureException with(@Nullable String message, @Nullable Exception cause, @Nullable Object... arguments) {
        return new UnexpectedFailureException(message, cause, arguments);
    }
    
    /**
     * Returns an unexpected failure exception with the given message.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Pure
    public static @Nonnull UnexpectedFailureException with(@Nullable String message, @Nullable Object... arguments) {
        return new UnexpectedFailureException(message, null, arguments);
    }
    
    /**
     * Returns an unexpected failure exception with the cause.
     */
    @Pure
    public static @Nonnull UnexpectedFailureException with(@Nullable Exception cause) {
        return new UnexpectedFailureException(null, cause);
    }
    
}
