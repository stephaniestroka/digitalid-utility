package net.digitalid.utility.contracts.exceptions;

/**
 * This exception indicates a violated precondition.
 */
public class PreconditionViolationException extends ContractViolationException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected PreconditionViolationException(String message, Object... arguments) {
        super(message, arguments);
    }
    
    /**
     * Returns a precondition violation exception with the given message.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static PreconditionViolationException with(String message, Object... arguments) {
        return new PreconditionViolationException(message, arguments);
    }
    
}
