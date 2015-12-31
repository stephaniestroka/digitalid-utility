package net.digitalid.utility.system.converter.exceptions;

import javax.annotation.Nonnull;

public class ConverterException extends Exception {
     
    protected ConverterException(@Nonnull Class<?> clazz) {
        super("Failed to find serializer for type '" + clazz + "'");
    }

    protected ConverterException(@Nonnull Class<?> clazz, @Nonnull Throwable throwable) {
        super("Failed to find serializer for type '" + clazz + "'", throwable);
    }
    
    public static @Nonnull
    ConverterException get(@Nonnull Class<?> clazz, @Nonnull Throwable throwable) {
        return new ConverterException(clazz, throwable);
    }
    
    public static @Nonnull
    ConverterException get(@Nonnull Class<?> clazz) {
        return new ConverterException(clazz);
    }
}
