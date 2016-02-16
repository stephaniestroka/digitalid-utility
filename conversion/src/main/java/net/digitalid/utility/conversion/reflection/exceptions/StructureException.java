package net.digitalid.utility.conversion.reflection.exceptions;

import javax.annotation.Nonnull;

/**
 * This exception is thrown if the structure of a convertible type is unexpected, e.g if no convertible fields are present, or if multiple recovery methods or constructors are declared.
 */
public class StructureException extends Exception {
    
    /**
     * Creates a new structure exception.
     * 
     * @param message the message propagated with this exception.
     */
    protected StructureException(@Nonnull String message) {
        super(message);
    }
    
    /**
     * Creates and returns a new structure exception.
     *
     * @param message the message propagated with this exception.
     */
    public static StructureException get(@Nonnull String message) {
        return new StructureException(message);
    }
    
}
