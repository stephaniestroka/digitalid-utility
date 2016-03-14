package net.digitalid.utility.functional.iterable.infinite;

import java.util.Iterator;

import javax.annotation.Nullable;

/**
 * An infinite iterator is an iterator that returns the same element infinitely.
 */
public class InfiniteIterator<T> implements Iterator<T> {
    
    private final @Nullable T element;
    
    /**
     * Creates a new infinite iterator.
     */
    public InfiniteIterator(@Nullable T element) {
        this.element = element;
    }
    
    @Override
    public boolean hasNext() {
        return true;
    }
    
    @Override
    public T next() {
        return element;
    }
    
}
