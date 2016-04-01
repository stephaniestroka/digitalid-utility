package net.digitalid.utility.contracts.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception indicates a violated precondition.
 */
@Immutable
public class PreconditionViolationException extends ContractViolationException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected PreconditionViolationException(@Nullable String message, @Nullable Object... arguments) {
        super(message, arguments);
    }
    
    /**
     * Returns a precondition violation exception with the given message.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Pure
    public static @Nonnull PreconditionViolationException with(@Nullable String message, @Nullable Object... arguments) {
        return new PreconditionViolationException(message, arguments);
    }
    
}
