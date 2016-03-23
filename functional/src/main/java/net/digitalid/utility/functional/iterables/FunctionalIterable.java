package net.digitalid.utility.functional.iterables;

import java.util.Iterator;

import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This interface extends the {@link Iterable} interface with functional methods.
 * 
 * @see InfiniteIterable
 * @see FiniteIterable
 */
public interface FunctionalIterable<T> extends Iterable<T> {
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    /**
     * Returns whether this iterable is empty.
     */
    @Pure
    public default boolean isEmpty() {
        return !iterator().hasNext();
    }
    
    /**
     * Returns the size of this iterable or the given limit if the size is greater than the limit.
     * 
     * @throws IndexOutOfBoundsException if the given limit is negative.
     */
    @Pure
    public default int size(int limit) {
        if (limit < 0) { throw new IndexOutOfBoundsException("The limit has to be non-negative but was " + limit + "."); }
        
        int size = 0;
        final Iterator<T> iterator = iterator();
        while (iterator.hasNext() && size < limit) {
            iterator.next();
            size++;
        }
        return size;
    }
    
    /**
     * Returns whether the size of this iterable is at most the given value.
     */
    @Pure
    public default boolean sizeAtMost(int value) {
        return size(value + 1) <= value;
    }
    
    /**
     * Returns whether the size of this iterable is at least the given value.
     */
    @Pure
    public default boolean sizeAtLeast(int value) {
        return size(value) == value;
    }
    
    /* -------------------------------------------------- Access -------------------------------------------------- */
    
    /**
     * Returns the object at the given index.
     * 
     * @throws IndexOutOfBoundsException if the given index is negative or greater or equal to the size of this iterable.
     */
    @Pure
    public default T get(int index) {
        int currentIndex = 0;
        for (T object : this) {
            if (currentIndex == index) { return object; }
            else { currentIndex += 1; }
        }
        throw new IndexOutOfBoundsException("The index has to be non-negative and smaller than the size but was " + index + ".");
    }
    
    /* -------------------------------------------------- Filter -------------------------------------------------- */
    
    /**
     * Filters the elements of this iterable with the given predicate.
     */
    @Pure
    public default FunctionalIterable<T> filter(Predicate<T> predicate) {
        // TODO: Override the return type in the subinterfaces. Throw UnsupportedOperationException here?
        return () -> new FilterIterator(this, predicate);
    }
    
    /* -------------------------------------------------- Mapping -------------------------------------------------- */
    
    // map(unaryFunction)
    // flatMap as well?
    
    /* -------------------------------------------------- Zipping -------------------------------------------------- */
    
    @Pure
    public default <T1> FiniteIterable<Pair<T, T1>> zipShortest(FiniteIterable<T1> iterable) {
        
    }
    
    @Pure
    public default <T1> FunctionalIterable<Pair<T, T1>> zipShortest(InfiniteIterable<T1> iterable) {
        throw new UnsupportedOperationException("This method has to be overriden in subtypes.");
    }
    
    @Pure
    public default <T1> FunctionalIterable<Pair<T, T1>> zipLongest(FiniteIterable<T1> iterable) {
        throw new UnsupportedOperationException("This method has to be overriden in subtypes.");
    }
    
    @Pure
    public default <T1> InfiniteIterable<Pair<T, T1>> zipLongest(InfiniteIterable<T1> iterable) {
        
    }
    
    /* -------------------------------------------------- Pruning -------------------------------------------------- */
    
    /**
     * Filters the elements of this iterable with the given predicate.
     */
    @Pure
    public default FunctionalIterable<T> skip(int number) {
        return () -> new FilterIterator(this, predicate);
    }
    
    /**
     * Filters the elements of this iterable with the given predicate.
     */
    @Pure
    public default FunctionalIterable<T> limit(int number) {
        return () -> new FilterIterator(this, predicate);
    }
    
    /**
     * Filters the elements of this iterable with the given predicate.
     */
    @Pure
    public default FunctionalIterable<T> extract(int fromIndex, int toIndex) {
        return () -> new FilterIterator(this, predicate);
    }
    
}
