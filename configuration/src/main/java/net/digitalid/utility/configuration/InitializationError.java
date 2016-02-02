package net.digitalid.utility.configuration;

import net.digitalid.utility.errors.CustomError;

/**
 * This error indicates a failed initialization.
 */
public class InitializationError extends CustomError {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates an initialization error with the given nullable message and nullable cause.
     */
    protected InitializationError(String message, Exception cause) {
        super(message, cause);
    }
    
    /**
     * Returns an initialization error with the given nullable message and nullable cause.
     */
    public static InitializationError with(String message, Exception cause) {
        return new InitializationError(message, cause);
    }
    
    /**
     * Returns an initialization error with the given nullable message.
     */
    public static InitializationError with(String message) {
        return new InitializationError(message, null);
    }
    
    /**
     * Returns an initialization error with the given nullable cause.
     */
    public static InitializationError with(Exception cause) {
        return new InitializationError(null, cause);
    }
    
}
