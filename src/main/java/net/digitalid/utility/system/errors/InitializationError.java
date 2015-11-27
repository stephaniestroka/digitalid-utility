package net.digitalid.utility.system.errors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.digitalid.utility.annotations.state.Immutable;
import net.digitalid.utility.annotations.state.Pure;

/**
 * This error is thrown when an error occurs during initialization.
 */
@Immutable
public class InitializationError extends FatalError {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new initialization error with the given message and cause.
     * 
     * @param message a string explaining the problem which has occurred.
     * @param cause the exception that caused this problem, if available.
     */
    protected InitializationError(@Nullable String message, @Nullable Throwable cause) {
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
    @Pure
    public static @Nonnull InitializationError get(@Nullable String message, @Nullable Throwable cause) {
        return new InitializationError(message, cause);
    }
    
    /**
     * Returns a new initialization error with the given message.
     * 
     * @param message a string explaining the problem which has occurred.
     * 
     * @return a new initialization error with the given message.
     */
    @Pure
    public static @Nonnull InitializationError get(@Nullable String message) {
        return new InitializationError(message, null);
    }
    
    /**
     * Returns a new initialization error with the given cause.
     * 
     * @param cause the exception that caused this problem, if available.
     * 
     * @return a new initialization error with the given cause.
     */
    @Pure
    public static @Nonnull InitializationError get(@Nullable Throwable cause) {
        return new InitializationError(null, cause);
    }
    
}
