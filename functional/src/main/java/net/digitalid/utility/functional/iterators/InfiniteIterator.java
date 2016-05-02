package net.digitalid.utility.functional.iterators;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This interface models an iterator that returns an infinite number of elements.
 */
@Mutable
public abstract class InfiniteIterator<E> extends ReadOnlyIterator<E> {
    
    /* -------------------------------------------------- Overridden Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public final boolean hasNext() {
        return true;
    }
    
}
