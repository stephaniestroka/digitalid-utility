package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.infinite.InfiniteIterator;

/**
 * An infinite non-nullable iterable is an iterable that holds an infinite iterator,
 * which returns a given non-null element infinitely.
 */
public class InfiniteNonNullableIterable<T> extends NonNullIterable<T> {
    
    /* -------------------------------------------------- Final Fields -------------------------------------------------- */
    
    /**
     * The non nullable element that is returned infinitely.
     */
    private final @Nonnull T element;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    InfiniteNonNullableIterable(@Nonnull T element) {
        this.element = element;
    }
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    @Override
    public Iterator<T> iterator() {
        return new InfiniteIterator<>(element);
    }
    
    @Override
    public int size() {
        return -1;
    }
    
}
