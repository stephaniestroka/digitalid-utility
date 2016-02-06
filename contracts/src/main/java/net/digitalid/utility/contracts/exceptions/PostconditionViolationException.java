package net.digitalid.utility.contracts.exceptions;

/**
 * This exception indicates a violated postcondition.
 */
public class PostconditionViolationException extends ContractViolationException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
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
