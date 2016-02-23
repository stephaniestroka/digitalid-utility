package net.digitalid.utility.exceptions;

/**
 * This exception indicates that an operation which should always succeed under normal circumstances failed.
 */
public class UnexpectedFailureException extends InternalException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected UnexpectedFailureException(String message, Exception cause, Object... arguments) {
        super(message, cause, arguments);
    }
    
    /**
     * Returns an unexpected failure exception with the given message and cause.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static UnexpectedFailureException with(String message, Exception cause, Object... arguments) {
        return new UnexpectedFailureException(message, cause, arguments);
    }
    
    /**
     * Returns an unexpected failure exception with the given message.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static UnexpectedFailureException with(String message, Object... arguments) {
        return new UnexpectedFailureException(message, null, arguments);
    }
    
    /**
     * Returns an unexpected failure exception with the cause.
     */
    public static UnexpectedFailureException with(Exception cause) {
        return new UnexpectedFailureException(null, cause);
    }
    
}
