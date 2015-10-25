package net.digitalid.utility.system.errors;

import javax.annotation.Nonnull;
import net.digitalid.utility.annotations.state.Immutable;

/**
 * This error is thrown when an error occurs which should never happen.
 */
@Immutable
public final class ShouldNeverHappenError extends FatalError {
    
    /**
     * Creates a new error which should never happen with the given message.
     * 
     * @param message a string explaining what happened nevertheless.
     */
    public ShouldNeverHappenError(@Nonnull String message) {
        super(message, null);
    }
    
    /**
     * Creates a new error which should never happen with the given cause.
     * 
     * @param cause a reference to the cause of the error.
     */
    public ShouldNeverHappenError(@Nonnull Throwable cause) {
        super(null, cause);
    }
    
    /**
     * Creates a new error which should never happen with the given message and cause.
     * 
     * @param message a string explaining what happened nevertheless.
     * @param cause a reference to the cause of the error.
     */
    public ShouldNeverHappenError(@Nonnull String message, @Nonnull Exception cause) {
        super(message, cause);
    }
    
}
