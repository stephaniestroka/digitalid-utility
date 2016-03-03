package net.digitalid.utility.generator.information.exceptions;

import javax.annotation.Nonnull;

/**
 * This exception is thrown if the content of a type is not as expected.
 */
public class UnexpectedTypeContentException extends Exception {
    
    /* -------------------------------------------------- Constructor with Message -------------------------------------------------- */
    
    private UnexpectedTypeContentException(@Nonnull String message) {
        super(message);
    }
    
    public static @Nonnull UnexpectedTypeContentException get(@Nonnull String message) {
        return new UnexpectedTypeContentException(message);
    }
    
    /* -------------------------------------------------- Constructor with Message and Cause -------------------------------------------------- */
    
    private UnexpectedTypeContentException(@Nonnull String message, @Nonnull Exception cause) {
        super(message, cause);
    }
    
    public static @Nonnull UnexpectedTypeContentException get(@Nonnull String message, @Nonnull Exception cause) {
        return new UnexpectedTypeContentException(message, cause);
    }
    
}
