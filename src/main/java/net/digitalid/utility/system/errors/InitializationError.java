package net.digitalid.utility.system.errors;

import javax.annotation.Nonnull;
import net.digitalid.utility.annotations.state.Immutable;

/**
 * This error is thrown when an error occurs during initialization.
 */
@Immutable
public final class InitializationError extends FatalError {
    
    /**
     * Creates a new initialization error with the given message.
     * 
     * @param message a string explaining the error that occurred.
     */
    public InitializationError(@Nonnull String message) {
        super(message, null);
    }
    
    /**
     * Creates a new initialization error with the given cause.
     * 
     * @param cause a reference to the cause of the error.
     */
    public InitializationError(@Nonnull Throwable cause) {
        super(null, cause);
    }
    
    /**
     * Creates a new initialization error with the given message and cause.
     * 
     * @param message a string explaining the error that occurred.
     * @param cause a reference to the cause of the error.
     */
    public InitializationError(@Nonnull String message, @Nonnull Throwable cause) {
        super(message, cause);
    }
    
}
