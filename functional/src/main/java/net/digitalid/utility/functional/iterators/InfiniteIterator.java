package net.digitalid.utility.functional.iterators;

import net.digitalid.utility.annotations.method.Pure;

/**
 * This interface models an iterator that returns an infinite number of elements.
 */
public interface InfiniteIterator<E> extends ReadOnlyIterator<E> {
    
    /* -------------------------------------------------- Overridden Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public default boolean hasNext() {
        return true;
    }
    
}
