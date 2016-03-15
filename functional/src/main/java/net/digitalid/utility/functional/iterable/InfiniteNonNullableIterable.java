package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.infinite.InfiniteIterator;

/**
 * An infinite non-nullable iterable is an iterable that holds an iterator
 * that returns a given non-null element infinitely.
 */
public class InfiniteNonNullableIterable<T> extends NonNullableIterable<T> {
    
    /* -------------------------------------------------- Element -------------------------------------------------- */
    
    /**
     * The non-nullable element that is returned infinitely.
     */
    private final @Nonnull T element;
    
    /**
     * Returns the element that is returned infinitely in the iterator.
     */
    public @Nonnull T getElement() {
        return element;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new infinite iterable from a non-nullable element.
     * The iterator of this iterable returns the element infinitely.
     */
    InfiniteNonNullableIterable(@Nonnull T element) {
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
