package net.digitalid.utility.functional.iterables;

import java.util.Collection;
import java.util.Iterator;

import net.digitalid.utility.functional.iterators.SingleIteratorBasedIterator;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This class implements the collection iterable interface based on a collection.
 */
public class CollectionBasedIterable<E> implements CollectionIterable<E> {
    
    /* -------------------------------------------------- Collection -------------------------------------------------- */
    
    private final Collection<? extends E> collection;
    
    /**
     * Returns the underlying collection of this iterable.
     */
    @Pure
    public Collection<? extends E> getCollection() {
        return collection;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected CollectionBasedIterable(Collection<? extends E> collection) {
        this.collection = collection;
    }
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Pure
    @Override
    public Iterator<E> iterator() {
        return new SingleIteratorBasedIterator<E, E>(collection.iterator()) {
    
            @Override public boolean hasNext() {
                return primaryIterator.hasNext();
            }
    
            @Override
            public E next() {
                return primaryIterator.next();
            }
            
        };
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Pure
    @Override
    public long size() {
        return collection.size();
    }
    
}
