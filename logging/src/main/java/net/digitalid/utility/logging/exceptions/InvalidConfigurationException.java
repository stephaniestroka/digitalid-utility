package net.digitalid.utility.logging.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception indicates that a configuration is invalid.
 */
@Immutable
public class InvalidConfigurationException extends ExternalException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected InvalidConfigurationException(@Nonnull String message, @Nullable Exception cause, @Captured @Nonnull @NullableElements Object... arguments) {
        super(message, cause, arguments);
    }
    
    /**
     * Returns an invalid configuration exception with the given message and cause.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Pure
    public static @Nonnull InvalidConfigurationException with(@Nonnull String message, @Nullable Exception cause, @Nonnull @NullableElements Object... arguments) {
        return new InvalidConfigurationException(message, cause, arguments);
    }
    
}
