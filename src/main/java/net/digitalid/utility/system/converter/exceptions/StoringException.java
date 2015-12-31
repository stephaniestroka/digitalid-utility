package net.digitalid.utility.system.converter.exceptions;

import javax.annotation.Nonnull;

public class StoringException extends Exception {
    
    /**
     * Creates a new internal exception with the given message.
     *
     * @param message a string explaining the illegal operation.
     */
    private StoringException(@Nonnull Class<?> clazz, @Nonnull String message) {
        super("Failed to store object of type '" + clazz + "': " + message);
    }

    private StoringException(@Nonnull Class<?> clazz, @Nonnull String message, @Nonnull Throwable throwable) {
        super("Failed to store object of type '" + clazz + "': " + message, throwable);
    }
    
    // TODO: maybe move to package of converter, so that we can use the package visibility instead of 'public'.
    public static @Nonnull StoringException get(@Nonnull Class<?> clazz, @Nonnull String message) {
        return new StoringException(clazz, message);
    }
    
    public static @Nonnull StoringException get(@Nonnull Class<?> clazz, @Nonnull String message, @Nonnull Throwable throwable) {
        return new StoringException(clazz, message, throwable);
    }
    
    public static @Nonnull StoringException get(@Nonnull Class<?> clazz, @Nonnull Throwable throwable) {
        return new StoringException(clazz, throwable.getMessage(), throwable);
    }
    
}
