package net.digitalid.utility.initialization.errors;

import net.digitalid.utility.errors.FatalError;

/**
 * This error is thrown when an error occurs during initialization.
 */
public class InitializationError extends FatalError {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new initialization error with the given message and cause.
     * 
     * @param message a string explaining the problem which has occurred.
     * @param cause the exception that caused this problem, if available.
     */
    protected InitializationError(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Returns a new initialization error with the given message and cause.
     * 
     * @param message a string explaining the problem which has occurred.
     * @param cause the exception that caused this problem, if available.
     * 
     * @return a new initialization error with the given message and cause.
     */
    public static InitializationError get(String message, Throwable cause) {
        return new InitializationError(message, cause);
    }
    
    /**
     * Returns a new initialization error with the given message.
     * 
     * @param message a string explaining the problem which has occurred.
     * 
     * @return a new initialization error with the given message.
     */
    public static InitializationError get(String message) {
        return new InitializationError(message, null);
    }
    
    /**
     * Returns a new initialization error with the given cause.
     * 
     * @param cause the exception that caused this problem, if available.
     * 
     * @return a new initialization error with the given cause.
     */
    public static InitializationError get(Throwable cause) {
        return new InitializationError(null, cause);
    }
    
}
