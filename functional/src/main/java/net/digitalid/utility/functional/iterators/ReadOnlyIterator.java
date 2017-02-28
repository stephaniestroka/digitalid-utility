package net.digitalid.utility.functional.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.functional.exceptions.IterationException;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class models an iterator whose elements cannot be removed.
 */
@Mutable
public abstract class ReadOnlyIterator<@Specifiable ELEMENT> implements Iterator<ELEMENT> {
    
    /* -------------------------------------------------- Supported Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public abstract boolean hasNext() throws IterationException;
    
    @Impure
    @Override
    public abstract ELEMENT next() throws IterationException, NoSuchElementException;
    
    /* -------------------------------------------------- Unsupported Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public final void remove() throws  UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    
}
