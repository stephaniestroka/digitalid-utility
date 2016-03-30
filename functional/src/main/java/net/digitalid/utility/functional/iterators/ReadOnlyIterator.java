package net.digitalid.utility.functional.iterators;

import java.util.Iterator;

import net.digitalid.utility.annotations.method.Pure;

/**
 * This interface models an iterator whose elements can only be read.
 */
public interface ReadOnlyIterator<E> extends Iterator<E> {
    
    /* -------------------------------------------------- Unsupported Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public default void remove() {
        throw new UnsupportedOperationException();
    }
    
}
