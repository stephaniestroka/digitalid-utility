package net.digitalid.utility.functional.iterators;

import java.util.Iterator;

/**
 * This class implements an iterator which is based on a primary and a secondary iterator.
 */
public abstract class DoubleIteratorBasedIterator<O, I0, I1> extends SingleIteratorBasedIterator<O, I0> {
    
    /* -------------------------------------------------- Secondary Iterator -------------------------------------------------- */
    
    /**
     * Stores the secondary iterator on which this iterator is based.
     */
    protected final Iterator<? extends I1> secondaryIterator;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected DoubleIteratorBasedIterator(Iterator<? extends I0> primaryIterator, Iterator<? extends I1> secondaryIterator) {
        super(primaryIterator);
        
        this.secondaryIterator = secondaryIterator;
    }
    
}
