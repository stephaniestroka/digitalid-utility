package net.digitalid.utility.validation.exceptions;

import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception indicates a violated invariant.
 */
@Immutable
public class InvariantViolationException extends ContractViolationException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates an invariant violation exception with the given message.
     */
    protected InvariantViolationException(String message) {
        super(message);
    }
    
    /**
     * Returns an invariant violation exception with the given message.
     */
    public static InvariantViolationException with(String message) {
        return new InvariantViolationException(message);
    }
    
}
