package net.digitalid.utility.contracts.exceptions;

/**
 * This exception indicates a violated precondition.
 */
public class PreconditionViolationException extends ContractViolationException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
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
