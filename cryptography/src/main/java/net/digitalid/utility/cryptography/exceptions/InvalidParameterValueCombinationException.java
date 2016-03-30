package net.digitalid.utility.cryptography.exceptions;

import javax.annotation.Nonnull;

import net.digitalid.utility.exceptions.InternalException;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This exception is thrown when a combination of parameter values is invalid.
 */
@Immutable
// TODO: This exception should almost certainly not be in this project and not extend internal exception.
public class InvalidParameterValueCombinationException extends InternalException {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new invalid combination exception with the given message.
     * 
     * @param message a string explaining which values cannot be combined.
     */
    protected InvalidParameterValueCombinationException(@Nonnull String message) {
        super(message);
    }
    
    /**
     * Returns a new invalid combination exception with the given message.
     * 
     * @param message a string explaining which values cannot be combined.
     * 
     * @return a new invalid combination exception with the given message.
     */
    @Pure
    public static @Nonnull InvalidParameterValueCombinationException get(@Nonnull String message) {
        return new InvalidParameterValueCombinationException(message);
    }
    
}
