package net.digitalid.utility.system.converter.exceptions;

import javax.annotation.Nonnull;

public class StoringException extends ConverterException {
    
    /**
     * Creates a new internal exception with the given message.
     *
     * @param message a string explaining the illegal operation.
     */
    private StoringException(@Nonnull Object object, @Nonnull String message) {
        super("Failed to store object of type '" + object.getClass() + "': " + message);
    }

    private StoringException(@Nonnull Object object, @Nonnull String message, @Nonnull Throwable throwable) {
        super("Failed to store object of type '" + object.getClass() + "': " + message, throwable);
    }
    
    // TODO: maybe move to package of converter, so that we can use the package visibility instead of 'public'.
    public static @Nonnull StoringException get(@Nonnull Object object, @Nonnull String message) {
        return new StoringException(object, message);
    }
    
    public static @Nonnull StoringException get(@Nonnull Object object, @Nonnull String message, @Nonnull Throwable throwable) {
        return new StoringException(object, message, throwable);
    }
}
