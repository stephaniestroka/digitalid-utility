package net.digitalid.utility.functional.iterators;

import java.util.NoSuchElementException;

import net.digitalid.utility.annotations.method.Pure;

/**
 * This class implements an array iterator that iterates over the given elements.
 */
public class ArrayIterator<E> implements ReadOnlyIterator<E> {
    
    /* -------------------------------------------------- Elements -------------------------------------------------- */
    
    protected final E[] elements;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    @SafeVarargs
    protected ArrayIterator(E... elements) {
        this.elements = elements;
    }
    
    /**
     * Returns a new array iterator that iterates over the given elements.
     */
    @Pure
    @SafeVarargs
    public static <E> ArrayIterator<E> with(E... elements) {
        return new ArrayIterator<>(elements);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    private int cursor = 0;
    
    @Pure
    @Override
    public boolean hasNext() {
        return cursor < elements.length;
    }
    
    @Override
    public E next() {
        if (hasNext()) { return elements[cursor++]; }
        else { throw new NoSuchElementException(); }
    }
    
}
