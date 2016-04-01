package net.digitalid.utility.functional.iterables;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.functional.interfaces.UnaryFunction;
import net.digitalid.utility.functional.iterators.PruningIterator;
import net.digitalid.utility.functional.iterators.ZippingIterator;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.index.Index;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This interface extends the {@link Iterable} interface with functional methods.
 * 
 * @see InfiniteIterable
 * @see FiniteIterable
 */
@Immutable
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
     * @throws IndexOutOfBoundsException if the given limit is non-positive.
     */
    @Pure
    public default @NonNegative int size(@Positive int limit) {
        if (limit <= 0) { throw new IndexOutOfBoundsException("The limit has to be positive but was " + limit + "."); }
        
        int size = 0;
        final @Nonnull Iterator<E> iterator = iterator();
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
    public default boolean isSize(@NonNegative int number) {
        return size(number + 1) == number;
    }
    
    /**
     * Returns whether the size of this iterable is at most the given value.
     */
    @Pure
    public default boolean sizeAtMost(@NonNegative int value) {
        return size(value + 1) <= value;
    }
    
    /**
     * Returns whether the size of this iterable is at least the given value.
     */
    @Pure
    public default boolean sizeAtLeast(@Positive int value) {
        return size(value) == value;
    }
    
    /* -------------------------------------------------- Element -------------------------------------------------- */
    
    /**
     * Returns the element at the given index.
     * 
     * @throws IndexOutOfBoundsException if the given index is negative or greater or equal to the size of this iterable.
     */
    @Pure
    public default @NonCapturable E get(@Index int index) {
        int currentIndex = 0;
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
    public @Nonnull FunctionalIterable<E> filter(@Nonnull Predicate<? super E> predicate);
    
    /**
     * Returns the elements of this iterable without the null values.
     */
    @Pure
    public @Nonnull FunctionalIterable<E> filterNulls();
    
    /* -------------------------------------------------- Mapping -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable mapped by the given function.
     */
    @Pure
    public <F> @Nonnull FunctionalIterable<F> map(@Nonnull UnaryFunction<? super E, ? extends F> function);
    
    /* -------------------------------------------------- Pruning -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable after discarding the given number of elements from the beginning.
     */
    @Pure
    public @Nonnull FunctionalIterable<E> skip(@Positive int number);
    
    /**
     * Returns the given number of elements from the beginning of this iterable.
     */
    @Pure
    public default @Nonnull FiniteIterable<E> limit(@Positive int number) {
        return () -> PruningIterator.with(iterator(), 0, number);
    }
    
    /**
     * Returns the elements of this iterable from the given start index to but not including the given end index.
     * If the end index is {@link Integer#MAX_VALUE}, the returned iterable iterates as long as this iterable does,
     * which means calling {@code extract(startIndex, Integer.MAX_VALUE)} is the same as {@code skip(startIndex)}.
     */
    @Pure
    public default @Nonnull FiniteIterable<E> extract(@Positive int startIndex, @Positive int endIndex) {
        return () -> PruningIterator.with(iterator(), startIndex, endIndex);
    }
    
    /* -------------------------------------------------- Zipping -------------------------------------------------- */
    
    /**
     * Returns the elements from this and the given iterable as pairs, where the i-th pair contains the i-th element of each iterable.
     * The returned iterable is truncated to the length of the shorter iterable.
     */
    @Pure
    public default <F> @Nonnull FiniteIterable<Pair<E, F>> zipShortest(@Nonnull FiniteIterable<? extends F> iterable) {
        return () -> ZippingIterator.with(iterator(), iterable.iterator(), true);
    }
    
    /**
     * Returns the elements from this and the given iterable as pairs, where the i-th pair contains the i-th element of each iterable.
     * The returned iterable is truncated to the length of the shorter iterable.
     */
    @Pure
    public <F> @Nonnull FunctionalIterable<Pair<E, F>> zipShortest(@Nonnull InfiniteIterable<? extends F> iterable);
    
    /**
     * Returns the elements from this and the given iterable as pairs, where the i-th pair contains the i-th element of each iterable.
     * The shorter iterable is extended to the length of the longer iterable with null values for the missing elements.
     */
    @Pure
    public <F> @Nonnull FunctionalIterable<Pair<E, F>> zipLongest(@Nonnull FiniteIterable<? extends F> iterable);
    
    /**
     * Returns the elements from this and the given iterable as pairs, where the i-th pair contains the i-th element of each iterable.
     * The shorter iterable is extended to the length of the longer iterable with null values for the missing elements.
     */
    @Pure
    public default <F> @Nonnull InfiniteIterable<Pair<E, F>> zipLongest(@Nonnull InfiniteIterable<? extends F> iterable) {
        return () -> ZippingIterator.with(iterator(), iterable.iterator(), false);
    }
    
    /* -------------------------------------------------- Flattening -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable with all collections up to the given level flattened.
     */
    @Pure
    public <F> @Nonnull FunctionalIterable<F> flatten(@Positive int level);
    
    /**
     * Returns the elements of this iterable with all collections directly contained in this iterable flattened.
     */
    @Pure
    public <F> @Nonnull FunctionalIterable<F> flattenOne();
    
    /**
     * Returns the elements of this iterable with all collections directly or indirectly contained in this iterable flattened.
     */
    @Pure
    public <F> @Nonnull FunctionalIterable<F> flattenAll();
    
}
