package net.digitalid.utility.functional.iterable.old;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.array.ArrayIterator;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * Wraps an array into a iterable with nullable elements.
 */
@Immutable
class ArrayNullableIterable<T> extends NullableIterable<T> {
    
    /**
     * The original iterable.
     */
    private final @Nonnull @NullableElements T[] array;
    
    /**
     * Creates a wrapper around the original array.
     */
    ArrayNullableIterable(@Nonnull @NullableElements T[] array) {
        this.array = array;
    }
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull @NullableElements Iterator<T> iterator() {
        return new ArrayIterator<>(array);
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Override
    public int size() {
        return array.length;
    }
    
}
