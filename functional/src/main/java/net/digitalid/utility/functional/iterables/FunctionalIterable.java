package net.digitalid.utility.functional.iterables;

import java.util.Iterator;

import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.functional.interfaces.UnaryFunction;
import net.digitalid.utility.functional.iterators.PruningIterator;
import net.digitalid.utility.functional.iterators.ZippingIterator;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.annotations.method.Pure;

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
    public default long size(long limit) {
        if (limit < 0) { throw new IndexOutOfBoundsException("The limit has to be non-negative but was " + limit + "."); }
        
        long size = 0;
        final Iterator<E> iterator = iterator();
        while (iterator.hasNext() && size < limit) {
            iterator.next();
            size++;
        }
        return size;
    }
    
    /**
     * Returns whether this iterable has the given number of elements.
     */
    @Pure
    public default boolean isSize(long number) {
        return size(number + 1) == number;
    }
    
    /**
     * Returns whether the size of this iterable is at most the given value.
     */
    @Pure
    public default boolean sizeAtMost(long value) {
        return size(value + 1) <= value;
    }
    
    /**
     * Returns whether the size of this iterable is at least the given value.
     */
    @Pure
    public default boolean sizeAtLeast(long value) {
        return size(value) == value;
    }
    
    /* -------------------------------------------------- Element -------------------------------------------------- */
    
    /**
     * Returns the element at the given index.
     * 
     * @throws IndexOutOfBoundsException if the given index is negative or greater or equal to the size of this iterable.
     */
    @Pure
    public default E get(long index) {
        long currentIndex = 0;
        for (E element : this) {
            if (currentIndex == index) { return element; }
            currentIndex += 1;
        }
        throw new IndexOutOfBoundsException("The index has to be non-negative and smaller than the size but was " + index + ".");
    }
    
    /* -------------------------------------------------- Filtering -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable filtered by the given predicate.
     */
    @Pure
    public FunctionalIterable<E> filter(Predicate<? super E> predicate);
    
    /**
     * Returns the elements of this iterable without the null values.
     */
    @Pure
    public FunctionalIterable<E> filterNulls();
    
    /* -------------------------------------------------- Mapping -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable mapped by the given function.
     */
    @Pure
    public <F> FunctionalIterable<F> map(UnaryFunction<? super E, ? extends F> function);
    
    /* -------------------------------------------------- Pruning -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable after discarding the given number of elements from the beginning.
     */
    @Pure
    public FunctionalIterable<E> skip(long number);
    
    /**
     * Returns the given number of elements from the beginning of this iterable.
     */
    @Pure
    public default FiniteIterable<E> limit(long number) {
        return () -> PruningIterator.with(iterator(), 0, number);
    }
    
    /**
     * Returns the elements of this iterable from the given start index to but not including the given end index.
     */
    @Pure
    public default FiniteIterable<E> extract(long startIndex, long endIndex) {
        return () -> PruningIterator.with(iterator(), startIndex, endIndex);
    }
    
    /* -------------------------------------------------- Zipping -------------------------------------------------- */
    
    /**
     * Returns the elements from this and the given iterable as pairs, where the i-th pair contains the i-th element of each iterable.
     * The returned iterable is truncated to the length of the shorter iterable.
     */
    @Pure
    public default <F> FiniteIterable<Pair<E, F>> zipShortest(FiniteIterable<? extends F> iterable) {
        return () -> ZippingIterator.with(iterator(), iterable.iterator(), true);
    }
    
    /**
     * Returns the elements from this and the given iterable as pairs, where the i-th pair contains the i-th element of each iterable.
     * The returned iterable is truncated to the length of the shorter iterable.
     */
    @Pure
    public <F> FunctionalIterable<Pair<E, F>> zipShortest(InfiniteIterable<? extends F> iterable);
    
    /**
     * Returns the elements from this and the given iterable as pairs, where the i-th pair contains the i-th element of each iterable.
     * The shorter iterable is extended to the length of the longer iterable with null values for the missing elements.
     */
    @Pure
    public <F> FunctionalIterable<Pair<E, F>> zipLongest(FiniteIterable<? extends F> iterable);
    
    /**
     * Returns the elements from this and the given iterable as pairs, where the i-th pair contains the i-th element of each iterable.
     * The shorter iterable is extended to the length of the longer iterable with null values for the missing elements.
     */
    @Pure
    public default <F> InfiniteIterable<Pair<E, F>> zipLongest(InfiniteIterable<? extends F> iterable) {
        return () -> ZippingIterator.with(iterator(), iterable.iterator(), false);
    }
    
    /* -------------------------------------------------- Flattening -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable with all collections up to the given level flattened.
     */
    @Pure
    public <F> FunctionalIterable<F> flatten(long level);
    
    /**
     * Returns the elements of this iterable with all collections directly contained in this iterable flattened.
     */
    @Pure
    public <F> FunctionalIterable<F> flattenOne();
    
    /**
     * Returns the elements of this iterable with all collections directly or indirectly contained in this iterable flattened.
     */
    @Pure
    public <F> FunctionalIterable<F> flattenAll();
    
}
