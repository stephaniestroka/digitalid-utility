package net.digitalid.utility.functional.iterable.infinite;

import java.util.Iterator;

import javax.annotation.Nullable;

/**
 * An infinite iterator is an iterator that returns the same element infinitely.
 */
public class InfiniteIterator<T> implements Iterator<T> {
    
    /**
     * The element that is returned infinitely.
     */
    private final @Nullable T element;
    
    /**
     * Creates a new infinite iterator with a given element.
     */
    public InfiniteIterator(@Nullable T element) {
        this.element = element;
    }
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Override
    public boolean hasNext() {
        return true;
    }
    
    @Override
    public T next() {
        return element;
    }
    
    /* -------------------------------------------------- Remove -------------------------------------------------- */
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() is not implemented.");
    }
    
}
