package net.digitalid.utility.system.errors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.digitalid.utility.annotations.state.Immutable;
import net.digitalid.utility.annotations.state.Pure;

/**
 * This error is thrown when an error occurs which should never happen.
 */
@Immutable
public final class ShouldNeverHappenError extends FatalError {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new error which should never happen with the given message and cause.
     * 
     * @param message a string explaining the problem which has occurred.
     * @param cause the exception that caused this problem, if available.
     */
    protected ShouldNeverHappenError(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Returns a new error which should never happen with the given message and cause.
     * 
     * @param message a string explaining the problem which has occurred.
     * @param cause the exception that caused this problem, if available.
     * 
     * @return a new error which should never happen with the given message and cause.
     */
    @Pure
    public static @Nonnull ShouldNeverHappenError get(@Nullable String message, @Nullable Throwable cause) {
        return new ShouldNeverHappenError(message, cause);
    }
    
    /**
     * Returns a new error which should never happen with the given message.
     * 
     * @param message a string explaining the problem which has occurred.
     * 
     * @return a new error which should never happen with the given message.
     */
    @Pure
    public static @Nonnull ShouldNeverHappenError get(@Nullable String message) {
        return new ShouldNeverHappenError(message, null);
    }
    
    /**
     * Returns a new error which should never happen with the given cause.
     * 
     * @param cause the exception that caused this problem, if available.
     * 
     * @return a new error which should never happen with the given cause.
     */
    @Pure
    public static @Nonnull ShouldNeverHappenError get(@Nullable Throwable cause) {
        return new ShouldNeverHappenError(null, cause);
    }
    
}
