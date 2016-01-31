package net.digitalid.utility.exceptions.internal;

import net.digitalid.utility.configuration.InitializationError;
import net.digitalid.utility.exceptions.InternalException;

/**
 * This exception indicates a failed initialization and is the recoverable variant of {@link InitializationError}.
 */
public class InitializationException extends InternalException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a new initialization exception with the given nullable message and nullable cause.
     */
    protected InitializationException(String message, Exception cause) {
        super(message, cause);
    }
    
    /**
     * Returns a new initialization exception with the given nullable message and nullable cause.
     */
    public static InitializationException with(String message, Exception cause) {
        return new InitializationException(message, cause);
    }
    
    /**
     * Returns a new initialization exception with the given nullable message.
     */
    public static InitializationException with(String message) {
        return new InitializationException(message, null);
    }
    
    /**
     * Returns a new initialization exception with the given nullable cause.
     */
    public static InitializationException with(Exception cause) {
        return new InitializationException(null, cause);
    }
    
}
