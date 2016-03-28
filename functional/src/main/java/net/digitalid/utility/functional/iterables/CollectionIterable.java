package net.digitalid.utility.functional.iterables;

import java.util.Collection;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This interface extends the finite iterable interface to provide a faster {@link #size()} implementation.
 * 
 * @see CollectionBasedIterable
 */
public interface CollectionIterable<E> extends FiniteIterable<E> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Wraps the given collection as a functional iterable.
     */
    @Pure
    public static <E> CollectionIterable<E> of(Collection<? extends E> collection) {
        return new CollectionBasedIterable<>(collection);
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Pure
    @Override
    public default boolean isEmpty() {
        return size() > 0;
    }
    
    @Pure
    @Override
    public default long size(long limit) {
        return Math.min(size(), limit);
    }
    
    @Pure
    @Override
    public abstract long size();
    
}
