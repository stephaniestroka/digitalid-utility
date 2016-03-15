package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nullable;

import net.digitalid.utility.functional.iterable.infinite.InfiniteIterator;

/**
 * An infinite nullable iterable is an iterable that holds an infinite iterator
 * that returns a given nullable element infinitely.
 */
public class InfiniteNullableIterable<T> extends NullableIterable<T> {
    
    /* -------------------------------------------------- Final Fields -------------------------------------------------- */
    
    /**
     * The nullable element that is returned infinitely.
     */
    private final @Nullable T element;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new infinite iterable from a nullable element.
     * The iterator of this iterable returns the element infinitely.
     */
    InfiniteNullableIterable(@Nullable T element) {
        this.element = element;
    }
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    @Override
    public Iterator<T> iterator() {
        return new InfiniteIterator<>(element);
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    /**
     * Returns a size of -1, indicating that the iterable is infinitely big.
     */
    @Override
    public int size() {
        return -1;
    }
    
}
