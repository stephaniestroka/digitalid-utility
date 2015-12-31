package net.digitalid.utility.system.converter.exceptions;

import javax.annotation.Nonnull;

public class RestoringException extends Exception {
    
    /**
     * Creates a new internal exception with the given message.
     *
     * @param message a string explaining the illegal operation.
     */
    private RestoringException(@Nonnull Class<?> type, String message) {
        super("Failed to restore object of type '" + type + "': " + message);
    }
    
    private RestoringException(@Nonnull Class<?> type, @Nonnull String message, @Nonnull Throwable t) {
        super("Failed to restore object of type '" + type + "': " + message, t);
    }

    // TODO: maybe move to package of converter, so that we can use the package visibility instead of 'public'.
    public static @Nonnull RestoringException get(@Nonnull Class<?> clazz, @Nonnull String message) {
        return new RestoringException(clazz, message);
    }
    
    public static @Nonnull RestoringException get(@Nonnull Class<?> clazz, @Nonnull String message, @Nonnull Throwable throwable) {
        return new RestoringException(clazz, message, throwable);
    }
    
    public static @Nonnull RestoringException get(@Nonnull Class<?> clazz, @Nonnull Throwable throwable) {
        return new RestoringException(clazz, throwable.getMessage(), throwable);
    }
}
