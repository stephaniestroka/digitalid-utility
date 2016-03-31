package net.digitalid.utility.functional.iterators;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.type.Functional;

/**
 * This interface models an iterator that returns an infinite number of elements.
 */
@Mutable
@Functional
public interface InfiniteIterator<E> extends ReadOnlyIterator<E> {
    
    /* -------------------------------------------------- Overridden Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public default boolean hasNext() {
        return true;
    }
    
}
