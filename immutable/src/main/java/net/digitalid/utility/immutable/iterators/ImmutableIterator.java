package net.digitalid.utility.immutable.iterators;

import java.util.Iterator;
import java.util.Objects;

/**
 * This class implements an immutable iterator.
 */
public class ImmutableIterator<E> implements Iterator<E> {
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    private final Iterator<E> iterator;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ImmutableIterator(Iterator<E> iterator) {
        this.iterator = Objects.requireNonNull(iterator);
    }
    
    /**
     * Returns an immutable iterator that captures the given iterator.
     */
    public static <E> ImmutableIterator<E> with(Iterator<E> iterator) {
        return new ImmutableIterator<>(iterator);
    }
    
    /* -------------------------------------------------- Delegated Operations -------------------------------------------------- */
    
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }
    
    @Override
    public E next() {
        return iterator.next();
    }
    
    /* -------------------------------------------------- Unsupported Operations -------------------------------------------------- */
    
    @Override
    public final void remove() {
        throw new UnsupportedOperationException();
    }
    
}
