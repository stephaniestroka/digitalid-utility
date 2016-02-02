package net.digitalid.utility.exceptions;

import net.digitalid.utility.exceptions.internal.InitializationException;
import net.digitalid.utility.exceptions.internal.UncoveredCaseException;
import net.digitalid.utility.logging.Log;

/**
 * An internal exception indicates a wrong use of the library.
 * All custom runtime exceptions extend this class.
 * 
 * @see InitializationException
 * @see UncoveredCaseException
 */
public abstract class InternalException extends RuntimeException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates an internal exception with the given message and cause.
     */
    protected InternalException(String message, Exception cause) {
        super(message, cause);
        
        Log.error("An internal exception occurred.", this);
    }
    
    /**
     * Creates an internal exception with the given message.
     */
    protected InternalException(String message) {
        this(message, null);
    }
    
}
