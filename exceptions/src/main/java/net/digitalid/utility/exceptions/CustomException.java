package net.digitalid.utility.exceptions;

import net.digitalid.utility.logging.Log;

/**
 * All custom exceptions extend this class except {@link InternalException}, which extends {@link RuntimeException}.
 * 
 * @see ExternalException
 */
public abstract class CustomException extends Exception {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a custom exception with the given message and cause.
     */
    protected CustomException(String message, Exception cause) {
        super(message, cause);
        
        Log.warning("A custom exception occurred.", this);
    }
    
    /**
     * Creates a custom exception with the given message.
     */
    protected CustomException(String message) {
        this(message, null);
    }
    
}
