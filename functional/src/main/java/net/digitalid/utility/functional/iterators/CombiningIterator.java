package net.digitalid.utility.functional.iterators;

import java.util.Iterator;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This class implements a combining iterator that iterates first over the elements of the first iterator and then over the elements of the second iterator.
 */
public class CombiningIterator<E> extends DoubleIteratorBasedIterator<E, E, E> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected CombiningIterator(Iterator<? extends E> primaryIterator, Iterator<? extends E> secondaryIterator) {
        super(primaryIterator, secondaryIterator);
    }
    
    /**
     * Returns a new combining iterator that iterates first over the elements of the first iterator and then over the elements of the second iterator.
     */
    @Pure
    public static <E> CombiningIterator<E> with(Iterator<? extends E> primaryIterator, Iterator<? extends E> secondaryIterator) {
        return new CombiningIterator<>(primaryIterator, secondaryIterator);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean hasNext() {
        return primaryIterator.hasNext() || secondaryIterator.hasNext();
    }
    
    @Override
    public E next() {
        if (primaryIterator.hasNext()) { return primaryIterator.next(); }
        else { return secondaryIterator.next(); }
    }
    
}
