package net.digitalid.utility.system.converter.exceptions;

import javax.annotation.Nonnull;

public class FieldSerializerException extends Exception {
    
    private FieldSerializerException(@Nonnull String message) {
        super(message);
    }

    private FieldSerializerException(@Nonnull String message, @Nonnull Throwable throwable) {
        super(message, throwable);
    }
    
    public static @Nonnull FieldSerializerException get(@Nonnull String message, @Nonnull Throwable throwable) {
        return new FieldSerializerException(message, throwable);
    }
    
    public static @Nonnull FieldSerializerException get(@Nonnull String message) {
        return new FieldSerializerException(message);
    }
}
