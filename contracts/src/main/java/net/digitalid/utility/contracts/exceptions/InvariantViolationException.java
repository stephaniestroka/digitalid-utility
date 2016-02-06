package net.digitalid.utility.contracts.exceptions;

/**
 * This exception indicates a violated invariant.
 */
public class InvariantViolationException extends ContractViolationException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
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
