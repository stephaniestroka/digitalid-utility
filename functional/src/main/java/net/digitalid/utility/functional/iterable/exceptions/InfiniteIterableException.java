package net.digitalid.utility.functional.iterable.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.exceptions.InternalException;
import net.digitalid.utility.validation.annotations.elements.NullableElements;

/**
 * This exception indicates that the operation cannot be completed on an infinite iterable without causing infinite computing time.
 * For example, an infinite iterable can never be converted to a list.
 */
public class InfiniteIterableException extends InternalException {
    
    /**
     * Creates a new infinite iterable exception with a message, a cause and message arguments.
     */
    private InfiniteIterableException(@Nonnull String message, @Nonnull Exception cause, @Nullable @NullableElements Object... arguments) {
        super(message, cause, arguments);
    }
    
    /**
     * Creates a new infinite iterable exception with a message and message arguments.
     */
    private InfiniteIterableException(@Nonnull String message, @Nullable @NullableElements Object... arguments) {
        super(message, arguments);
    }
    
    /**
     * Returns a new infinite iterable exception with a message, a cause and message arguments.
     */
    public static @Nonnull InfiniteIterableException with(@Nonnull String message, @Nullable Exception cause, @Nullable @NullableElements Object... arguments) {
        return new InfiniteIterableException(message, cause, arguments);
    }
    
    /**
     * Returns a new infinite iterable exception with a message and message arguments.
     */
    public static @Nonnull InfiniteIterableException with(@Nonnull String message, @Nullable @NullableElements Object... arguments) {
        return new InfiniteIterableException(message, arguments);
    }
    
}
