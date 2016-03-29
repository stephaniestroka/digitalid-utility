package net.digitalid.utility.functional.iterators;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This class implements a repeating iterator that repeats the given object infinitely.
 */
public class RepeatingIterator<E> implements InfiniteIterator<E> {
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    protected final E object;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected RepeatingIterator(E object) {
        this.object = object;
    }
    
    /**
     * Returns a new repeating iterator that repeats the given object infinitely.
     */
    @Pure
    public static <E> RepeatingIterator<E> with(E object) {
        return new RepeatingIterator<>(object);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Override
    public E next() {
        return object;
    }
    
}
