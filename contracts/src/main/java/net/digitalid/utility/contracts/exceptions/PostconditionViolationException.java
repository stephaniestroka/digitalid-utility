package net.digitalid.utility.contracts.exceptions;

/**
 * This exception indicates a violated postcondition.
 */
public class PostconditionViolationException extends ContractViolationException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected PostconditionViolationException(String message, Object... arguments) {
        super(message, arguments);
    }
    
    /**
     * Returns a postcondition violation exception with the given message.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static PostconditionViolationException with(String message, Object... arguments) {
        return new PostconditionViolationException(message, arguments);
    }
    
}
