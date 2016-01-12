package net.digitalid.utility.system.errors;

import javax.annotation.Nullable;
import net.digitalid.utility.annotations.state.Immutable;
import net.digitalid.utility.system.logger.Log;

/**
 * This exception is thrown when a fatal error occurs.
 * 
 * @see InitializationError
 * @see ShouldNeverHappenError
 */
@Immutable
public abstract class FatalError extends Error {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new fatal error with the given message and cause.
     * 
     * @param message a string explaining the problem which has occurred.
     * @param cause the exception that caused this problem, if available.
     */
    protected FatalError(@Nullable String message, @Nullable Throwable cause) {
        super(message == null ? "A fatal error occurred." : message, cause);
        
        Log.error("A fatal error occurred.", this);
    }
    
}
