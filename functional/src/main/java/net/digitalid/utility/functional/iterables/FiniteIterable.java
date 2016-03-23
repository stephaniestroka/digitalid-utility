package net.digitalid.utility.functional.iterables;

import java.util.Comparator;

import net.digitalid.utility.functional.interfaces.BinaryOperator;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * Description.
 * 
 * @see CollectionIterable
 */
public interface FiniteIterable<T> extends FunctionalIterable<T> {
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    /**
     * Returns the size of this iterable.
     */
    @Pure
    public default int size() {
        return size(Integer.MAX_VALUE);
    }
    
    /* -------------------------------------------------- Index -------------------------------------------------- */
    
    @Pure
    public default int firstIndexOf(T object) {
        
    }
    
    @Pure
    public default int lastIndexOf(T object) {
        
    }
    
    /* -------------------------------------------------- Contain -------------------------------------------------- */
    
    // contains(T object)
    // containsAll(Collection<?> collection) or Iterable?
    
    /* -------------------------------------------------- Intersect -------------------------------------------------- */
    
    /* -------------------------------------------------- Exclude -------------------------------------------------- */
    
    /* -------------------------------------------------- Combine -------------------------------------------------- */
    
    @Pure
    public default FiniteIterable<T> combine(FiniteIterable<T> iterable) {
        
    }
    
    @Pure
    public default InfiniteIterable<T> combine(InfiniteIterable<T> iterable) {
        
    }
    
    /* -------------------------------------------------- Find -------------------------------------------------- */
    
    // findFirst
    // findLast
    // findUnique
    
    /* -------------------------------------------------- Match -------------------------------------------------- */
    
    // matchAny
    // matchAll
    
    /* -------------------------------------------------- Reduction -------------------------------------------------- */
    
    /**
     * Returns the value reduced by the given operator or null if this iterable is empty.
     */
    @Pure
    public default T reduce(BinaryOperator<T> operator) {
        return null;
    }
    
    // reduce with identity element
    
    // min and max with comparator
    
    /* -------------------------------------------------- Inspection -------------------------------------------------- */
    
    // inspect(consumer) or forEach(consumer)
    
    /* -------------------------------------------------- Sorting -------------------------------------------------- */
    
    /**
     * Filters the elements of this iterable with the given predicate.
     */
    @Pure
    public default FunctionalIterable<T> sorted() {
        return () -> new FilterIterator(this, predicate);
    }
    
    /**
     * Filters the elements of this iterable with the given predicate.
     */
    @Pure
    public default FunctionalIterable<T> sorted(Comparator<? super T> comparator) {
        return () -> new FilterIterator(this, predicate);
    }
    
    /* -------------------------------------------------- Unique -------------------------------------------------- */
    
    /**
     * Filters the elements of this iterable with the given predicate.
     */
    @Pure
    public default FunctionalIterable<T> unique() { // or rather distinct?
        return () -> new FilterIterator(this, predicate);
    }
    
    /* -------------------------------------------------- Array -------------------------------------------------- */
    
    /**
     * Filters the elements of this iterable with the given predicate.
     */
    @Pure
    public default T[] toArray() {
        return () -> new FilterIterator(this, predicate);
    }
    
}
