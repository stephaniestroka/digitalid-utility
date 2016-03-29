package net.digitalid.utility.functional.iterators;

import java.util.Iterator;

/**
 * This class implements an iterator which is based on a single iterator.
 * 
 * @see DoubleIteratorBasedIterator
 */
public abstract class SingleIteratorBasedIterator<O, I0> implements ReadOnlyIterator<O> {
    
    /* -------------------------------------------------- Primary Iterator -------------------------------------------------- */
    
    /**
     * Stores the primary iterator on which this iterator is based.
     */
    protected final Iterator<? extends I0> primaryIterator;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected SingleIteratorBasedIterator(Iterator<? extends I0> primaryIterator) {
        this.primaryIterator = primaryIterator;
    }
    
}
