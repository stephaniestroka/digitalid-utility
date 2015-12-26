package net.digitalid.utility.system.converter.exceptions;

import javax.annotation.Nonnull;

public class SerializerException extends Exception {
     
    protected SerializerException(@Nonnull Class<?> clazz) {
        super("Failed to find serializer for type '" + clazz + "'");
    }

    protected SerializerException(@Nonnull Class<?> clazz, @Nonnull Throwable throwable) {
        super("Failed to find serializer for type '" + clazz + "'", throwable);
    }
    
    public static @Nonnull SerializerException get(@Nonnull Class<?> clazz, @Nonnull Throwable throwable) {
        return new SerializerException(clazz, throwable);
    }
    
    public static @Nonnull SerializerException get(@Nonnull Class<?> clazz) {
        return new SerializerException(clazz);
    }
}
