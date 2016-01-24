package net.digitalid.utility.errors;

/**
 * This error should never happen due to the design of the library.
 */
public final class ShouldNeverHappenError extends CustomError {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new error which should never happen with the given nullable message and nullable cause.
     */
    protected ShouldNeverHappenError(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Returns a new error which should never happen with the given nullable message and nullable cause.
     */
    public static ShouldNeverHappenError of(String message, Throwable cause) {
        return new ShouldNeverHappenError(message, cause);
    }
    
    /**
     * Returns a new error which should never happen with the given nullable message.
     */
    public static ShouldNeverHappenError of(String message) {
        return new ShouldNeverHappenError(message, null);
    }
    
    /**
     * Returns a new error which should never happen with the given nullable cause.
     */
    public static ShouldNeverHappenError of(Throwable cause) {
        return new ShouldNeverHappenError(null, cause);
    }
    
}
