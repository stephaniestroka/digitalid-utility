package net.digitalid.utility.functional.iterables;

import java.util.Collection;
import java.util.Iterator;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This class implements the functional iterable interface based on a collection.
 */
public class CollectionBasedIterable<E> implements CollectionIterable<E> {
    
    /* -------------------------------------------------- Collection -------------------------------------------------- */
    
    private final Collection<E> collection;
    
    /**
     * Returns the underlying collection of this iterable.
     */
    @Pure
    public Collection<E> getCollection() {
        return collection;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected CollectionBasedIterable(Collection<E> collection) {
        this.collection = collection;
    }
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Pure
    @Override
    public Iterator<E> iterator() {
        return collection.iterator();
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Pure
    @Override
    public int size() {
        return collection.size();
    }
    
}
