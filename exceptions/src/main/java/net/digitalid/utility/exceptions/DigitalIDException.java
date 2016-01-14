package net.digitalid.utility.exceptions;


import net.digitalid.utility.logging.Log;

/**
 * All custom exceptions extend this class.
 */
public abstract class DigitalIDException extends Exception {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a new Digital ID exception with the given message and cause.
     * 
     * @param message a string explaining the problem which has occurred.
     * @param cause the exception that caused this problem, if available.
     */
    protected DigitalIDException(String message, Exception cause) {
        super(message, cause);
        
        Log.warning("A problem occurred.", this);
    }
    
    /**
     * Creates a new Digital ID exception with the given message.
     * 
     * @param message a string explaining the problem which has occurred.
     */
    protected DigitalIDException(String message) {
        this(message, null);
    }
    
}
