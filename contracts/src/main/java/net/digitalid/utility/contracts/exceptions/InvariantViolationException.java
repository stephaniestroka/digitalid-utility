package net.digitalid.utility.contracts.exceptions;

/**
 * This exception indicates a violated invariant.
 */
public class InvariantViolationException extends ContractViolationException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected InvariantViolationException(String message, Object... arguments) {
        super(message, arguments);
    }
    
    /**
     * Returns an invariant violation exception with the given message.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static InvariantViolationException with(String message, Object... arguments) {
        return new InvariantViolationException(message, arguments);
    }
    
}
