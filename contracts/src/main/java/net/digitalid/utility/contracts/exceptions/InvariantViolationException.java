package net.digitalid.utility.contracts.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception indicates a violated invariant.
 */
@Immutable
public class InvariantViolationException extends ContractViolationException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected InvariantViolationException(@Nullable String message, @Nullable Object... arguments) {
        super(message, arguments);
    }
    
    /**
     * Returns an invariant violation exception with the given message.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Pure
    public static @Nonnull InvariantViolationException with(@Nullable String message, @Nullable Object... arguments) {
        return new InvariantViolationException(message, arguments);
    }
    
}
