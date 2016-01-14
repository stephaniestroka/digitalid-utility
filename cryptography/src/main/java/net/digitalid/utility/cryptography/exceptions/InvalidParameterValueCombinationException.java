package net.digitalid.utility.cryptography.exceptions;

import javax.annotation.Nonnull;
import net.digitalid.utility.validation.state.Immutable;
import net.digitalid.utility.validation.state.Pure;
import net.digitalid.utility.exceptions.external.InvalidEncodingException;

/**
 * This exception is thrown when a combination of parameter values is invalid.
 */
@Immutable
public class InvalidParameterValueCombinationException extends InvalidEncodingException {
    
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
