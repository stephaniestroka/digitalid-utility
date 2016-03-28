package net.digitalid.utility.functional.iterable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.function.binary.NonNullToNonNullBinaryFunction;
import net.digitalid.utility.functional.function.binary.NonNullToNullableBinaryFunction;
import net.digitalid.utility.functional.function.unary.NonNullToNonNullUnaryFunction;
import net.digitalid.utility.functional.function.unary.NonNullToNullableUnaryFunction;
import net.digitalid.utility.functional.iterable.exceptions.InfiniteIterableException;
import net.digitalid.utility.functional.predicate.NonNullablePredicate;
import net.digitalid.utility.tuples.pair.NonNullablePair;
import net.digitalid.utility.tuples.quartet.NonNullableQuartet;
import net.digitalid.utility.tuples.triplet.NonNullableTriplet;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * The non-nullable iterable has the same properties as the {@link NullableIterable}, but only
 * holds and retrieves non-nullable elements.
 * Therefore, functions and predicates applied on this iterable must not be able to handle null values.
 * The filtering and transformation functions are applied lazily on a call to {@link Iterator#next()}.
 */
@Stateless
public abstract class NonNullableIterable<T> extends NullableIterable<T> {
    
    /* -------------------------------------------------- Filter -------------------------------------------------- */
    
    /**
     * Returns an iterable which does not have any null elements.
     * Since we have a non-null iterable, this function is a no-op and returns itself.
     */
    @Pure
    @Override
    public @Nonnull NonNullableIterable<T> filterNonNull() {
        return this;
    }
    
    /**
     * Applies a filter to a non-null iterable and returns a new iterable.
     */
    public @Nonnull NonNullableIterable<T> filter(@Nonnull NonNullablePredicate<T> predicate) {
        return new FilterNonNullIterable<>(this, predicate);
    }
    
    /* -------------------------------------------------- Find First -------------------------------------------------- */
    
    /**
     * Filters and returns the first element that matches the given predicate.
     * If no element was found, null is returned.
     */
    public @Nullable T findFirst(@Nonnull NonNullablePredicate<T> predicate) {
        final @Nonnull NullableIterable<T> iterable = filter(predicate);
        final @Nonnull Iterator<T> iterator = iterable.iterator();
        return iterator.hasNext() ? iterator.next() : null;
    }
    
    /**
     * Filters the first element that matches the given predicate. If an element was found, a given function is applied and the result is returned. Otherwise, null is returned.
     */
    public @Nullable <O> O findFirst(@Nonnull NonNullablePredicate<T> predicate, @Nonnull NonNullToNonNullUnaryFunction<T, O> function) {
        final @Nonnull NullableIterable<T> iterable = filter(predicate);
        final @Nonnull Iterator<T> iterator = iterable.iterator();
        if (iterator.hasNext()) {
            final @Nonnull T element = iterator.next();
            return function.apply(element);
        } else {
            return null;
        }
    }
    
    /* -------------------------------------------------- Map -------------------------------------------------- */
    
    /**
     * Takes a non-null iterable and maps its elements to a new iterable by passing each element through a given function.
     * The function maps non-null elements to non-null elements, therefore the resulting iterable
     * contains only non-nullable elements.
     */
    public @Nonnull <E> NonNullableIterable<E> map(@Nonnull NonNullToNonNullUnaryFunction<? super T, E> function) {
        return new MapNonNullIterable<>(this, function);
    }
    
    /**
     * Takes a non-null iterable and maps its elements to a new iterable by passing each element through a given function.
     * The function maps non-null elements to nullable elements, therefore the resulting iterable
     * contains nullable elements.
     */
    public @Nonnull <E> NullableIterable<E> map(@Nonnull NonNullToNullableUnaryFunction<T, E> function) {
        return new MapNullableIterable<>(this, function);
    }
    
    /* -------------------------------------------------- Reduce -------------------------------------------------- */
    
    /**
     * Returns a value of type <i>&lt;T&gt;</i> which is calculated using the given function.
     * The function expects two values: The first value is the output of the previous calculation, 
     * or an empty element if we are at the beginning, and the second is the next element in the iterable.
     * The return value might be null.
     */
    @Pure
    public @Nullable T reduce(@Nonnull NonNullToNullableBinaryFunction<T, T, T> function) {
        return reduceInternal(function);
    }
    
    /**
     * Returns a value of type <i>&lt;T&gt;</i> which is calculated using the given function.
     * The function expects two values: The first value is the output of the previous calculation, 
     * or an empty element if we are at the beginning, and the second is the next element in the iterable.
     * The return value cannot be null.
     */
    @Pure
    public @Nonnull T reduce(@Nonnull NonNullToNonNullBinaryFunction<T, T, T> function) {
        @Nullable T reducedValue = reduceInternal(function);
        assert reducedValue != null;
        final @Nonnull T result = reducedValue;
        return result;
    }
    
    /* -------------------------------------------------- Zip -------------------------------------------------- */
    
    /**
     * Zips this iterable with another non-nullable-elements iterable. An iterable of non-nullable pairs is returned. 
     * If one iterable is shorter, excess elements of the longer iterable are discarded.
     */
    public @Nonnull <T2> NonNullableIterable<NonNullablePair<T,T2>> zipNonNull(@Nonnull NonNullableIterable<T2> iterable) {
        return new ZipToNonNullablePairNonNullIterable<>(this, iterable);
    } 
    
    /**
     * Zips this iterable with two other non-nullable-elements iterables. An iterable of non-nullable triplets is returned.
     * If one iterable is shorter, excess elements of the longer iterable are discarded.
     */
    public @Nonnull <T2, T3> NonNullableIterable<NonNullableTriplet<T,T2, T3>> zipNonNull(@Nonnull NonNullableIterable<T2> iterable2, @Nonnull NonNullableIterable<T3> iterable3) {
        return new ZipToNonNullableTripletNonNullIterable<>(this, iterable2, iterable3);
    } 
    
    /**
     * Zips this iterable with three other non-nullable-elements iterables. An iterable of non-nullable quartets is returned.
     * If one iterable is shorter, excess elements of the longer iterable are discarded.
     */
    public @Nonnull <T2, T3, T4> NonNullableIterable<NonNullableQuartet<T,T2, T3, T4>> zipNonNull(@Nonnull NonNullableIterable<T2> iterable2, @Nonnull NonNullableIterable<T3> iterable3, @Nonnull NonNullableIterable<T4> iterable4) {
        return new ZipToNonNullableQuartetNonNullIterable<>(this, iterable2, iterable3, iterable4);
    }
    
    /* -------------------------------------------------- Combine -------------------------------------------------- */
    
    /**
     * Combines this iterable with another iterable with non-nullable elements.
     */
    @SafeVarargs
    public final @Nonnull NonNullableIterable<T> combine(@Nonnull NonNullableIterable<T>... iterables) {
        final @Nonnull @NonNullableElements List<NonNullableIterable<T>> combiningIterables = new ArrayList<>();
        combiningIterables.add(this);
        combiningIterables.addAll(Arrays.asList(iterables));
        return new CombineNonNullIterable<>(combiningIterables);
    }
    
    /* -------------------------------------------------- List -------------------------------------------------- */
    
    @Override
    public @Nonnull @NonNullableElements List<T> toList() {
        int size = size();
        if (size >= 0){
            final @Nonnull @NonNullableElements List<T> list = new ArrayList<>(size);
            for (@Nonnull T element : this) {
                list.add(element);
            }
            return list;
        } else {
            throw InfiniteIterableException.with("An infinite iterable cannot be converted to a finite list.");
        }
    }
    
}
