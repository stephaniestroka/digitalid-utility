package net.digitalid.utility.exceptions.external;

import net.digitalid.utility.exceptions.DigitalIDException;

/**
 * An external exception is caused by another party.
 * 
 * @see InvalidEncodingException
 */
public abstract class ExternalException extends DigitalIDException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a new external exception with the given message and cause.
     * 
     * @param message a string explaining the problem which has occurred.
     * @param cause the exception that caused this problem, if available.
     */
    protected ExternalException(String message, Exception cause) {
        super(message, cause);
    }
    
    /**
     * Creates a new external exception with the given message.
     * 
     * @param message a string explaining the problem which has occurred.
     */
    protected ExternalException(String message) {
        super(message);
    }
    
}
