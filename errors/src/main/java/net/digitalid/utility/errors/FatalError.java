package net.digitalid.utility.errors;

import net.digitalid.utility.logging.Log;

/**
 * This exception is thrown when a fatal error occurs.
 * 
 * @see InitializationError
 * @see ShouldNeverHappenError
 */
public abstract class FatalError extends Error {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new fatal error with the given message and cause.
     * 
     * @param message a string explaining the problem which has occurred.
     * @param cause the exception that caused this problem, if available.
     */
    protected FatalError(String message, Throwable cause) {
        super(message == null ? "A fatal error occurred." : message, cause);
        
        Log.error("A fatal error occurred.", this);
    }
    
}
