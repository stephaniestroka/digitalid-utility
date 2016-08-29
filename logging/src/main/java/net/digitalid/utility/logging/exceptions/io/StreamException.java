package net.digitalid.utility.logging.exceptions.io;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.logging.exceptions.ExternalException;
import net.digitalid.utility.validation.annotations.elements.NullableElements;

/**
 * This exception is thrown whenever a network or file stream is corrupted by external causes.
 */
public class StreamException extends ExternalException {
    
    protected StreamException(@Nullable String message, @Nullable Exception cause, @Captured @Nonnull @NullableElements Object... arguments) {
        super(message, cause, arguments);
    }
    
    public static @Nonnull StreamException with(@Nullable String message, @Nullable Exception cause, @Captured @Nonnull @NullableElements Object... arguments) {
        return new StreamException(message, cause, arguments);
    }
    
    public static @Nonnull StreamException with(@Nullable String message, @Captured @Nonnull @NullableElements Object... arguments) {
        return new StreamException(message, null, arguments);
    }
    
}
