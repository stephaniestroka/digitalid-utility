package net.digitalid.utility.functional.iterable.old;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.array.ArrayIterator;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * Wraps an array into a iterable with non-nullable elements.
 */
@Immutable
class ArrayNonNullableIterable<T> extends NonNullableIterable<T> {
    
    /**
     * The original array.
     */
    private final @Nonnull @NonNullableElements T[] array;
    
    /**
     * Creates a wrapper around the original array.
     */
    ArrayNonNullableIterable(@Nonnull @NonNullableElements T[] array) {
        this.array = array;
    }
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
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
