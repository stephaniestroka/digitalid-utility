package net.digitalid.utility.functional.iterators;

import java.util.NoSuchElementException;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This class implements a reversing iterator that iterates over the given elements in reverse order.
 */
public class ReversingIterator<E> implements ReadOnlyIterator<E> {
    
    /* -------------------------------------------------- Array -------------------------------------------------- */
    
    protected final E[] elements;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    @SafeVarargs
    protected ReversingIterator(E... elements) {
        this.elements = elements;
        this.cursor = elements.length - 1;
    }
    
    /**
     * Creates a new reversing iterator that iterates over the given elements in reverse order.
     */
    @Pure
    @SafeVarargs
    public static <E> ReversingIterator<E> with(E... elements) {
        return new ReversingIterator<>(elements);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    private int cursor;
    
    @Pure
    @Override
    public boolean hasNext() {
        return cursor >= 0;
    }
    
    @Override
    public E next() {
        if (hasNext()) { return elements[cursor--]; }
        else { throw new NoSuchElementException(); }
    }
    
}
