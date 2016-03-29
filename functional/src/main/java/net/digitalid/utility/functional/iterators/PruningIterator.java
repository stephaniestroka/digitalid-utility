package net.digitalid.utility.functional.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This class implements a pruning iterator that iterates over the elements of the given iterator from the given start index to but not including the given end index.
 */
public class PruningIterator<E> extends SingleIteratorBasedIterator<E, E> {
    
    /* -------------------------------------------------- Indexes -------------------------------------------------- */
    
    protected final long startIndex;
    
    protected final long endIndex;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected PruningIterator(Iterator<? extends E> primaryIterator, long startIndex, long endIndex) {
        super(primaryIterator);
        
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }
    
    /**
     * Returns a new pruning iterator that iterates over the elements of the given iterator from the given start index to but not including the given end index.
     */
    @Pure
    public static <E> PruningIterator<E> with(Iterator<? extends E> iterator, long startIndex, long endIndex) {
        return new PruningIterator<>(iterator, startIndex, endIndex);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    private long currentIndex = 0;
    
    @Pure
    @Override
    public boolean hasNext() {
        while (currentIndex < startIndex && primaryIterator.hasNext()) {
            primaryIterator.next();
            currentIndex += 1;
        }
        return currentIndex < endIndex && primaryIterator.hasNext();
    }
    
    @Override
    public E next() {
        if (hasNext()) {
            currentIndex += 1;
            return primaryIterator.next();
        } else {
            throw new NoSuchElementException();
        }
    }
    
}
