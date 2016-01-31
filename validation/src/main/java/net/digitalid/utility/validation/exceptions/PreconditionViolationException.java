package net.digitalid.utility.validation.exceptions;

import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception indicates a violated precondition.
 */
@Immutable
public class PreconditionViolationException extends ContractViolationException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a precondition violation exception with the given message.
     */
    protected PreconditionViolationException(String message) {
        super(message);
    }
    
    /**
     * Returns a precondition violation exception with the given message.
     */
    public static PreconditionViolationException with(String message) {
        return new PreconditionViolationException(message);
    }
    
}
