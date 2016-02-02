package net.digitalid.utility.errors;

/**
 * This error should never happen due to the design of the library.
 */
public final class ShouldNeverHappenError extends CustomError {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates an error which should never happen with the given nullable message and nullable cause.
     */
    protected ShouldNeverHappenError(String message, Exception cause) {
        super(message, cause);
    }
    
    /**
     * Returns an error which should never happen with the given nullable message and nullable cause.
     */
    public static ShouldNeverHappenError with(String message, Exception cause) {
        return new ShouldNeverHappenError(message, cause);
    }
    
    /**
     * Returns an error which should never happen with the given nullable message.
     */
    public static ShouldNeverHappenError with(String message) {
        return new ShouldNeverHappenError(message, null);
    }
    
    /**
     * Returns an error which should never happen with the given nullable cause.
     */
    public static ShouldNeverHappenError with(Exception cause) {
        return new ShouldNeverHappenError(null, cause);
    }
    
}
