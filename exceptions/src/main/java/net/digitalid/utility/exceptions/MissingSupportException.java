package net.digitalid.utility.exceptions;

/**
 * This exception indicates that the runtime system does not support a required feature.
 */
public class MissingSupportException extends InternalException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected MissingSupportException(String message, Exception cause) {
        super(message, cause);
    }
    
    /**
     * Returns a missing support exception with the given message and cause.
     */
    public static MissingSupportException with(String message, Exception cause) {
        return new MissingSupportException(message, cause);
    }
    
    /**
     * Returns a missing support exception with the given message.
     */
    public static MissingSupportException with(String message) {
        return new MissingSupportException(message, null);
    }
    
    /**
     * Returns a missing support exception with the given cause.
     */
    public static MissingSupportException with(Exception cause) {
        return new MissingSupportException(null, cause);
    }
    
}
