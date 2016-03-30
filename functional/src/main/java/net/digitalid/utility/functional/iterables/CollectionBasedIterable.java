package net.digitalid.utility.functional.iterables;

import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.functional.iterators.SingleIteratorBasedIterator;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class implements the collection iterable interface based on a collection.
 */
@Immutable
public class CollectionBasedIterable<E> implements CollectionIterable<E> {
    
    /* -------------------------------------------------- Collection -------------------------------------------------- */
    
    private final @Nonnull Collection<? extends E> collection;
    
    /**
     * Returns the underlying collection of this iterable.
     */
    @Pure
    public @Nonnull Collection<? extends E> getCollection() {
        return collection;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected CollectionBasedIterable(@Nonnull Collection<? extends E> collection) {
        this.collection = collection;
    }
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull Iterator<E> iterator() {
        return new SingleIteratorBasedIterator<E, E>(collection.iterator()) {
            
            @Pure
            @Override
            public boolean hasNext() {
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
    public @NonNegative long size() {
        return collection.size();
    }
    
}
