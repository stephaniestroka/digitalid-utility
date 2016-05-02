package net.digitalid.utility.functional.iterators;

import java.util.Iterator;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This interface models an iterator whose elements cannot be removed.
 */
@Mutable
public abstract class ReadOnlyIterator<E> implements Iterator<E> {
    
    /* -------------------------------------------------- Unsupported Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public final void remove() {
        throw new UnsupportedOperationException();
    }
    
}
