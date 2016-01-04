package net.digitalid.utility.conversion.exceptions;

import javax.annotation.Nonnull;
import net.digitalid.utility.exceptions.internal.InternalException;

public class ConverterNotFoundException extends InternalException {
     
    protected ConverterNotFoundException(@Nonnull Class<?> clazz) {
        super("Failed to find serializer for type '" + clazz + "'");
    }

    protected ConverterNotFoundException(@Nonnull Class<?> clazz, @Nonnull Throwable throwable) {
        super("Failed to find serializer for type '" + clazz + "'", throwable);
    }
    
    public static @Nonnull
    ConverterNotFoundException get(@Nonnull Class<?> clazz, @Nonnull Throwable throwable) {
        return new ConverterNotFoundException(clazz, throwable);
    }
    
    public static @Nonnull
    ConverterNotFoundException get(@Nonnull Class<?> clazz) {
        return new ConverterNotFoundException(clazz);
    }
}
