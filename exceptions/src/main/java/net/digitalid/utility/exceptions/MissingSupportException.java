package net.digitalid.utility.exceptions;

/**
 * This exception indicates that the runtime system does not support a required feature.
 */
public class MissingSupportException extends InternalException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected MissingSupportException(String message, Exception cause, Object... arguments) {
        super(message, cause, arguments);
    }
    
    /**
     * Returns a missing support exception with the given message and cause.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static MissingSupportException with(String message, Exception cause, Object... arguments) {
        return new MissingSupportException(message, cause, arguments);
    }
    
    /**
     * Returns a missing support exception with the given message.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static MissingSupportException with(String message, Object... arguments) {
        return new MissingSupportException(message, null, arguments);
    }
    
    /**
     * Returns a missing support exception with the given cause.
     */
    public static MissingSupportException with(Exception cause) {
        return new MissingSupportException(null, cause);
    }
    
}
