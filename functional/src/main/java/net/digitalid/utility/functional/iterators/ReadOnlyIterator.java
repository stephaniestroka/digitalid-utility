package net.digitalid.utility.functional.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.functional.exceptions.FailedIterationException;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class models an iterator whose elements cannot be removed.
 */
@Mutable
public abstract class ReadOnlyIterator<E> implements Iterator<E> {
    
    /* -------------------------------------------------- Supported Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public abstract boolean hasNext() throws FailedIterationException;
    
    @Impure
    @Override
    public abstract E next() throws FailedIterationException, NoSuchElementException;
    
    /* -------------------------------------------------- Unsupported Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public final void remove() throws  UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    
}
