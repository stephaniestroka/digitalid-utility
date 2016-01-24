package net.digitalid.utility.exceptions;

/**
 * An external exception is caused by another party.
 */
public abstract class ExternalException extends CustomException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates an external exception with the given message and cause.
     */
    protected ExternalException(String message, Exception cause) {
        super(message, cause);
    }
    
    /**
     * Creates an external exception with the given message.
     */
    protected ExternalException(String message) {
        super(message);
    }
    
}
