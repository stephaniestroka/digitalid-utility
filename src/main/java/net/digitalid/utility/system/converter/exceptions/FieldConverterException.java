package net.digitalid.utility.system.converter.exceptions;

import javax.annotation.Nonnull;

public class FieldConverterException extends Exception {
    
    private FieldConverterException(@Nonnull String fieldName, @Nonnull String message) {
        super("The converter for field '" + fieldName + "' cannot be instantiated: " + message);
    }

    private FieldConverterException(@Nonnull String fieldName, @Nonnull String message, @Nonnull Throwable throwable) {
        super("The converter for field '" + fieldName + "' cannot be instantiated: " + message, throwable);
    }
    
    public static @Nonnull
    FieldConverterException get(@Nonnull String fieldName, @Nonnull String message, @Nonnull Throwable throwable) {
        return new FieldConverterException(fieldName, message, throwable);
    }
    
    public static @Nonnull
    FieldConverterException get(@Nonnull String fieldName, @Nonnull String message) {
        return new FieldConverterException(fieldName, message);
    }
}
