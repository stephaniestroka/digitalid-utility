package net.digitalid.utility.exceptions;

/**
 * This exception indicates that an operation which should always succeed under normal circumstances failed.
 */
public class UnexpectedFailureException extends InternalException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected UnexpectedFailureException(String message, Exception cause) {
        super(message, cause);
    }
    
    /**
     * Returns an unexpected failure exception with the given message and cause.
     */
    public static UnexpectedFailureException with(String message, Exception cause) {
        return new UnexpectedFailureException(message, cause);
    }
    
    /**
     * Returns an unexpected failure exception with the given message.
     */
    public static UnexpectedFailureException with(String message) {
        return new UnexpectedFailureException(message, null);
    }
    
    /**
     * Returns an unexpected failure exception with the cause.
     */
    public static UnexpectedFailureException with(Exception cause) {
        return new UnexpectedFailureException(null, cause);
    }
    
}
