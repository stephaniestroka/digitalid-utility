package net.digitalid.utility.validation.validator.exceptions;

import javax.annotation.Nonnull;

import net.digitalid.utility.exceptions.ExternalException;

/**
 *
 */
public class ValidationFailedException extends ExternalException {
    
    protected ValidationFailedException(@Nonnull String message) {
        super(message);
    }

    protected ValidationFailedException(@Nonnull String message, @Nonnull Exception cause) {
        super(message, cause);
    }

    public static ValidationFailedException get(@Nonnull String message, @Nonnull Exception cause) {
        return new ValidationFailedException(message, cause);
    }

    public static ValidationFailedException get(@Nonnull String message) {
        return new ValidationFailedException(message);
    }

}
