package net.digitalid.utility.generator.information.type.exceptions;

import javax.annotation.Nonnull;

/**
 * This exception is thrown if the content of a type is not as expected, e.g. if more than one recover methods are present or if abstract methods exist that are not getters or setters.
 */
public class UnsupportedTypeException extends Exception {
    
    /* -------------------------------------------------- Constructor with Message -------------------------------------------------- */
    
    private UnsupportedTypeException(@Nonnull String message) {
        super(message);
    }
    
    public static @Nonnull UnsupportedTypeException get(@Nonnull String message) {
        return new UnsupportedTypeException(message);
    }
    
    /* -------------------------------------------------- Constructor with Message and Cause -------------------------------------------------- */
    
    private UnsupportedTypeException(@Nonnull String message, @Nonnull Exception cause) {
        super(message, cause);
    }
    
    public static @Nonnull UnsupportedTypeException get(@Nonnull String message, @Nonnull Exception cause) {
        return new UnsupportedTypeException(message, cause);
    }
    
}
