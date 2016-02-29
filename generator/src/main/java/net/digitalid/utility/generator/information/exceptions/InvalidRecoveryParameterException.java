package net.digitalid.utility.generator.information.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.exceptions.InternalException;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 *
 */
public class InvalidRecoveryParameterException extends InternalException {
    
    protected InvalidRecoveryParameterException(@Nonnull String message, @Nullable Exception cause, @Nullable @NonNullableElements Object... arguments) {
        super(message, cause, arguments);
    }
    
    public static @Nonnull InvalidRecoveryParameterException with(@Nonnull String parameterName) {
        return new InvalidRecoveryParameterException("Failed to retrieve field for recovery parameter with name '" + parameterName + "'", null);
                
    }
    
}
