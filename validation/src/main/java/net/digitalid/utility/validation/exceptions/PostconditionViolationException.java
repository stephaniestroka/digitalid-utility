package net.digitalid.utility.validation.exceptions;

import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception indicates a violated postcondition.
 */
@Immutable
public class PostconditionViolationException extends ContractViolationException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a postcondition violation exception with the given message.
     */
    protected PostconditionViolationException(String message) {
        super(message);
    }
    
    /**
     * Returns a postcondition violation exception with the given message.
     */
    public static PostconditionViolationException with(String message) {
        return new PostconditionViolationException(message);
    }
    
}
