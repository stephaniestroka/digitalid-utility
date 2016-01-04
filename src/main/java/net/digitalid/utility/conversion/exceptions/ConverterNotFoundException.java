package net.digitalid.utility.conversion.exceptions;

import javax.annotation.Nonnull;
import net.digitalid.utility.exceptions.internal.InternalException;

/**
 * The converter-not-found exception is thrown when a converter for a specific format is not found for a specific type. It is characterized as an internal exception, since the cause of the exception is a missing type converter, which indicates a wrong use of the library.
 */
public class ConverterNotFoundException extends InternalException {
    
    /**
     * Creates a converter-not-found exception with a given type.
     * 
     * @param type the type for which no converter could be found.
     */
    protected ConverterNotFoundException(@Nonnull Class<?> type) {
        super("Failed to find serializer for type '" + type + "'");
    }
    
    /**
     * Creates a converter-not-found exception with a given type and a given exception.
     * 
     * @param type the type for which no converter could be found.
     * @param exception the exception which is the cause of the converter-not-found exception.
     */
    protected ConverterNotFoundException(@Nonnull Class<?> type, @Nonnull Exception exception) {
        super("Failed to find serializer for type '" + type + "'", exception);
    }

    /**
     * Creates and returns a converter-not-found exception with a given type and a given exception.
     *
     * @param type the type for which no converter could be found.
     * @param exception the exception which is the cause of the converter-not-found exception.
     */
    public static @Nonnull
    ConverterNotFoundException get(@Nonnull Class<?> type, @Nonnull Exception exception) {
        return new ConverterNotFoundException(type, exception);
    }

    /**
     * Creates and returns a converter-not-found exception with a given type.
     *
     * @param type the type for which no converter could be found.
     */
    public static @Nonnull
    ConverterNotFoundException get(@Nonnull Class<?> type) {
        return new ConverterNotFoundException(type);
    }
    
}
