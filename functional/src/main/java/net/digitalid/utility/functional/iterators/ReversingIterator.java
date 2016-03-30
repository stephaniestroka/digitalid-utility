package net.digitalid.utility.functional.iterators;

import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.reference.Capturable;
import net.digitalid.utility.annotations.type.Mutable;

/**
 * This class implements a reversing iterator that iterates over the given elements in reverse order.
 */
@Mutable
public class ReversingIterator<E> implements ReadOnlyIterator<E> {
    
    /* -------------------------------------------------- Elements -------------------------------------------------- */
    
    protected final @Nonnull E[] elements;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    @SafeVarargs
    protected ReversingIterator(E... elements) {
        this.elements = elements;
        this.cursor = elements.length - 1;
    }
    
    /**
     * Returns a new reversing iterator that iterates over the given elements in reverse order.
     */
    @Pure
    @SafeVarargs
    public static <E> @Capturable @Nonnull ReversingIterator<E> with(E... elements) {
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
