package net.digitalid.utility.configuration;

import net.digitalid.utility.errors.CustomError;

/**
 * This error indicates a failed initialization.
 */
public class InitializationError extends CustomError {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new initialization error with the given nullable message and nullable cause.
     */
    protected InitializationError(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Returns a new initialization error with the given nullable message and nullable cause.
     */
    public static InitializationError of(String message, Throwable cause) {
        return new InitializationError(message, cause);
    }
    
    /**
     * Returns a new initialization error with the given nullable message.
     */
    public static InitializationError of(String message) {
        return new InitializationError(message, null);
    }
    
    /**
     * Returns a new initialization error with the given nullable cause.
     */
    public static InitializationError of(Throwable cause) {
        return new InitializationError(null, cause);
    }
    
}
