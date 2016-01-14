package net.digitalid.utility.errors;

/**
 * This error is thrown when an error occurs which should never happen.
 */
public final class ShouldNeverHappenError extends FatalError {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new error which should never happen with the given message and cause.
     * 
     * @param message a string explaining the problem which has occurred.
     * @param cause the exception that caused this problem, if available.
     */
    protected ShouldNeverHappenError(String message, Throwable cause) {
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
    public static ShouldNeverHappenError get(String message, Throwable cause) {
        return new ShouldNeverHappenError(message, cause);
    }
    
    /**
     * Returns a new error which should never happen with the given message.
     * 
     * @param message a string explaining the problem which has occurred.
     * 
     * @return a new error which should never happen with the given message.
     */
    public static ShouldNeverHappenError get(String message) {
        return new ShouldNeverHappenError(message, null);
    }
    
    /**
     * Returns a new error which should never happen with the given cause.
     * 
     * @param cause the exception that caused this problem, if available.
     * 
     * @return a new error which should never happen with the given cause.
     */
    public static ShouldNeverHappenError get(Throwable cause) {
        return new ShouldNeverHappenError(null, cause);
    }
    
}
