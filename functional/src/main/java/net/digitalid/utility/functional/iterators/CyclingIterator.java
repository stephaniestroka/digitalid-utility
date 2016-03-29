package net.digitalid.utility.functional.iterators;

import java.util.Iterator;

import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This class implements a cycling iterator that iterates over the elements of the given iterable indefinitely.
 */
public class CyclingIterator<E> implements ReadOnlyIterator<E> {
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    protected final FiniteIterable<? extends E> iterable;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected CyclingIterator(FiniteIterable<? extends E> iterable) {
        this.iterable = iterable;
        this.iterator = iterable.iterator();
        this.hasNext = iterator.hasNext();
    }
    
    /**
     * Returns a new cycling iterator that iterates over the elements of the given iterable indefinitely.
     */
    @Pure
    public static <E> CyclingIterator<E> with(FiniteIterable<? extends E> iterable) {
        return new CyclingIterator<>(iterable);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    private Iterator<? extends E> iterator;
    
    private final boolean hasNext;
    
    @Pure
    @Override
    public boolean hasNext() {
        return hasNext;
    }
    
    @Override
    public E next() {
        if (!iterator.hasNext()) { iterator = iterable.iterator(); }
        return iterator.next();
    }
    
}
