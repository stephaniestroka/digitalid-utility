package net.digitalid.utility.conversion.exceptions;

import javax.annotation.Nonnull;

import net.digitalid.utility.exceptions.external.ExternalException;

public class RecoveryException extends ExternalException {
    
    /**
     * Creates a new internal exception with the given message.
     *
     * @param message a string explaining the illegal operation.
     */
    private RecoveryException(@Nonnull Class<?> type, String message) {
        super("Failed to restore object of type '" + type + "': " + message);
    }
    
    private RecoveryException(@Nonnull Class<?> type, @Nonnull String message, @Nonnull Exception exception) {
        super("Failed to restore object of type '" + type + "': " + message, exception);
    }
    
    public static @Nonnull
    RecoveryException get(@Nonnull Class<?> clazz, @Nonnull String message) {
        return new RecoveryException(clazz, message);
    }
    
    public static @Nonnull
    RecoveryException get(@Nonnull Class<?> clazz, @Nonnull String message, @Nonnull Exception exception) {
        return new RecoveryException(clazz, message, exception);
    }
    
    public static @Nonnull
    RecoveryException get(@Nonnull Class<?> clazz, @Nonnull Exception exception) {
        return new RecoveryException(clazz, exception.getMessage(), exception);
    }
    
}
