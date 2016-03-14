package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.array.ArrayIterator;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * Wraps an ordinary iterable into a fluent iterable.
 */
@Immutable
class ArrayNonNullableIterable<T> extends NonNullIterable<T> {
    
    /**
     * The original iterable.
     */
    private final @Nonnull @NonNullableElements T[] array;
    
    /**
     * Creates a wrapper around the original array.
     */
    ArrayNonNullableIterable(@Nonnull @NonNullableElements T[] array) {
        this.array = array;
    }
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Iterator<T> iterator() {
        return new ArrayIterator<>(array);
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Override
    public int size() {
        return array.length;
    }
    
}
