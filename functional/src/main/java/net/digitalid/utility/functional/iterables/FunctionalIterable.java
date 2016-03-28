package net.digitalid.utility.functional.iterables;

import java.util.Iterator;

import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.functional.interfaces.UnaryFunction;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This interface extends the {@link Iterable} interface with functional methods.
 * 
 * @see InfiniteIterable
 * @see FiniteIterable
 */
public interface FunctionalIterable<E> extends Iterable<E> {
    
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
        final Iterator<E> iterator = iterator();
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
     * Returns the element at the given index.
     * 
     * @throws IndexOutOfBoundsException if the given index is negative or greater or equal to the size of this iterable.
     */
    @Pure
    public default E get(int index) {
        int currentIndex = 0;
        for (E element : this) {
            if (currentIndex == index) { return element; }
            currentIndex += 1;
        }
        throw new IndexOutOfBoundsException("The index has to be non-negative and smaller than the size but was " + index + ".");
    }
    
    /* -------------------------------------------------- Filter -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable filtered by the given predicate.
     */
    @Pure
    public default FunctionalIterable<E> filter(Predicate<? super E> predicate) {
        throw new UnsupportedOperationException("This method has to be overriden in subtypes.");
    }
    
    /* -------------------------------------------------- Mapping -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable mapped by the given function.
     */
    @Pure
    public default <F> FunctionalIterable<E> map(UnaryFunction<? super E, ? extends F> function) {
        throw new UnsupportedOperationException("This method has to be overriden in subtypes.");
    }
    
    /* -------------------------------------------------- Pruning -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable after discarding the given number of elements from the beginning.
     */
    @Pure
    public default FunctionalIterable<E> skip(int number) {
        throw new UnsupportedOperationException("This method has to be overriden in subtypes.");
    }
    
    /**
     * Returns the given number of elements from the beginning of this iterable.
     */
    @Pure
    public default FiniteIterable<E> limit(int number) {
        return () -> new SequenceIterator(iterator(), 0, number);
    }
    
    /**
     * Returns the elements of this iterable from the given index to but not including the second index.
     */
    @Pure
    public default FiniteIterable<E> extract(int fromIndex, int toIndex) {
        return () -> new SequenceIterator(iterator(), fromIndex, toIndex);
    }
    
    /* -------------------------------------------------- Zipping -------------------------------------------------- */
    
    /**
     * Returns the elements from this and the given iterable as pairs, where the i-th pair contains the i-th element of each iterable.
     * The returned iterable is truncated to the length of the shorter iterable.
     */
    @Pure
    public default <F> FiniteIterable<Pair<E, F>> zipShortest(FiniteIterable<F> iterable) {
        return () -> new ZipShortestIterator(iterator(), iterable.iterator());
    }
    
    /**
     * Returns the elements from this and the given iterable as pairs, where the i-th pair contains the i-th element of each iterable.
     * The returned iterable is truncated to the length of the shorter iterable.
     */
    @Pure
    public default <F> FunctionalIterable<Pair<E, F>> zipShortest(InfiniteIterable<F> iterable) {
        throw new UnsupportedOperationException("This method has to be overriden in subtypes.");
    }
    
    /**
     * Returns the elements from this and the given iterable as pairs, where the i-th pair contains the i-th element of each iterable.
     * The shorter iterable is extended to the length of the longer iterable with null values for the missing elements.
     */
    @Pure
    public default <F> FunctionalIterable<Pair<E, F>> zipLongest(FiniteIterable<F> iterable) {
        throw new UnsupportedOperationException("This method has to be overriden in subtypes.");
    }
    
    /**
     * Returns the elements from this and the given iterable as pairs, where the i-th pair contains the i-th element of each iterable.
     * The shorter iterable is extended to the length of the longer iterable with null values for the missing elements.
     */
    @Pure
    public default <F> InfiniteIterable<Pair<E, F>> zipLongest(InfiniteIterable<F> iterable) {
        return () -> new ZipLongestIterator(iterator(), iterable.iterator());
    }
    
    /* -------------------------------------------------- Flattening -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable with all collections up to the given level flattened.
     */
    @Pure
    public default <F> FunctionalIterable<F> flatten(int level) {
        throw new UnsupportedOperationException("This method has to be overriden in subtypes.");
        return () -> new FlattenIterator(iterator(), level);
    }
    
    /**
     * Returns the elements of this iterable with all collections directly contained in this iterable flattened.
     */
    @Pure
    public default <F> FunctionalIterable<F> flattenOne() {
        throw new UnsupportedOperationException("This method has to be overriden in subtypes.");
    }
    
    /**
     * Returns the elements of this iterable with all collections directly or indirectly contained in this iterable flattened.
     */
    @Pure
    public default <F> FunctionalIterable<F> flattenAll() {
        throw new UnsupportedOperationException("This method has to be overriden in subtypes.");
    }
    
}
