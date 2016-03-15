package net.digitalid.utility.functional.iterable;

import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * Wraps an ordinary collection into an iterable that can be used to filter or transform its elements.
 */
@Immutable
class WrappingNullableIterable<T> extends NullableIterable<T> {
    
    /**
     * The original iterable with nullable elements.
     */
    private final @Nonnull @NullableElements Iterable<T> iterable;
    
    /**
     * The size of the iterable.
     */
    private final int size;
    
    /**
     * Creates a wrapper around the original collection.
     */
    WrappingNullableIterable(@Nonnull @NullableElements Collection<T> collection) {
        this.iterable = collection;
        this.size = collection.size();
    }
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull @NullableElements Iterator<T> iterator() {
        return iterable.iterator();
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Override
    public int size() {
        return size;
    }
    
}
