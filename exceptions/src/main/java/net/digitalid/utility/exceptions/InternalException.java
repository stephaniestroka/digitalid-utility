package net.digitalid.utility.exceptions;

/**
 * An internal exception indicates a wrong use of the library.
 */
public class InternalException extends CustomException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates an internal exception with the given message and cause.
     */
    protected InternalException(String message, Exception cause) {
        super(message, cause);
    }
    
    /**
     * Creates an internal exception with the given message.
     */
    protected InternalException(String message) {
        super(message);
    }
    
    /**
     * Returns an internal exception with the given message and cause.
     */
    public static InternalException of(String message, Exception cause) {
        return new InternalException(message, cause);
    }
    
    /**
     * Returns an internal exception with the given message.
     */
    public static InternalException of(String message) {
        return new InternalException(message);
    }
    
    /**
     * Returns an internal exception with the given cause.
     */
    public static InternalException of(Exception cause) {
        return new InternalException(cause.getMessage(), cause);
    }
    
}
