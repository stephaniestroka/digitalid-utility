package net.digitalid.utility.functional.iterable.old;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.interfaces.BinaryFunction;
import net.digitalid.utility.functional.function.binary.NullableToNonNullBinaryFunction;
import net.digitalid.utility.functional.function.binary.NullableToNullableBinaryFunction;
import net.digitalid.utility.functional.function.unary.NullableToNonNullUnaryFunction;
import net.digitalid.utility.functional.function.unary.NullableToNullableUnaryFunction;
import net.digitalid.utility.functional.iterable.exceptions.InfiniteIterableException;
import net.digitalid.utility.functional.iterable.filter.predicate.implementation.FilterNonNullablePredicate;
import net.digitalid.utility.functional.iterable.zip.ZipStrategy;
import net.digitalid.utility.functional.predicate.NullablePredicate;
import net.digitalid.utility.tuples.NullablePair;
import net.digitalid.utility.tupless.quartet.NullableQuartet;
import net.digitalid.utility.tupless.triplet.NullableTriplet;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * This class represents an iterable on which filtering and transformation functions can be applied. 
 * The elements of this iterable may be null, which means that functions and predicates applied on this iterable must be able to handle null values.
 * The filtering and transformation functions are applied lazily on a call to {@link Iterator#next()}.
 * 
 * @see NonNullableIterable
 */
@Stateless
public abstract class NullableIterable<T> implements Iterable<T> {
    
    /* -------------------------------------------------- Wrapping -------------------------------------------------- */
    
    /**
     * Returns a new non-nullable elements iterable for the given collection.
     */
    public static @Nonnull <T> NonNullableIterable<T> ofNonNullableElements(@Nonnull @NonNullableElements Collection<T> collection) {
        return new WrappingNonNullIterable<>(collection);
    }
    
    /**
     * Returns a new non-nullable elements iterable for the given array of type <i>&lt;T&gt;</i>.
     */
    public static @Nonnull <T> NonNullableIterable<T> ofNonNullableElements(@Nonnull @NonNullableElements T[] array) {
        return new ArrayNonNullableIterable<>(array);
    }
    
    /**
     * Returns a new infinite iterable that returns the same element of type <i>&lt;T&gt;</i> infinitely. 
     * The element may not be null.
     */
    public static @Nonnull <T> NonNullableIterable<T> ofNonNullableElement(@Nonnull T element) {
        return new InfiniteNonNullableIterable<>(element);
    }
    
    /**
     * Returns a new iterable for the given collection that may contain null elements.
     */
    public static @Nonnull <T> NullableIterable<T> ofNullableElements(@Nonnull @NullableElements Collection<T> collection) {
        return new WrappingNullableIterable<>(collection);
    }
    
    /**
     * Returns a new iterable that may contain null elements for the given array of type <i>&lt;T&gt;</i>.
     */
    public static @Nonnull <T> NullableIterable<T> ofNullableElements(@Nonnull @NullableElements T[] array) {
        return new ArrayNullableIterable<>(array);
    }
    
    /**
     * Returns a new infinite iterable that returns the same element of type <i>&lt;T&gt;</i> infinitely. 
     * The element may be null.
     */
    public static @Nonnull <T> NullableIterable<T> ofNullableElement(@Nullable T element) {
        return new InfiniteNullableIterable<>(element);
    }
    
    /* -------------------------------------------------- Filter -------------------------------------------------- */
    
    /**
     * Filters all elements that are non-null and returns an iterable with non-null elements.
     */
    public @Nonnull NonNullableIterable<T> filterNonNull() {
        return new FilterNonNullIterable<>(this, new FilterNonNullablePredicate<T>());
    }
    
    /**
     * Filters the elements of an iterable using a given predicate.
     */
    public @Nonnull NullableIterable<T> filter(@Nonnull NullablePredicate<T> predicate) {
        return new FilterNullableIterable<>(this, predicate);
    }
    
    /* -------------------------------------------------- Find First -------------------------------------------------- */
    
    /**
     * Filters and returns the first element that matches the given predicate.
     */
    public @Nullable T findFirst(@Nonnull NullablePredicate<T> predicate) {
        final @Nonnull NullableIterable<T> iterable = filter(predicate);
        final @Nonnull Iterator<T> iterator = iterable.iterator();
        return iterator.hasNext() ? iterator.next() : null;
    }
    
    /* -------------------------------------------------- Map -------------------------------------------------- */
    
    /**
     * Maps the elements of this iterable to elements that are non-null, using a given function.
     */
    public @Nonnull @NonNullableElements <E> NonNullableIterable<E> map(@Nonnull NullableToNonNullUnaryFunction<T, E> function) {
        return new MapNonNullIterable<>(this, function);
    }
    
    /**
     * Maps the elements of this iterable to elements that are nullable, using a given function.
     */
    public @Nonnull @NullableElements <E> NullableIterable<E> map(@Nonnull NullableToNullableUnaryFunction<T, E> function) {
        return new MapNullableIterable<>(this, function);
    }
    
    /* -------------------------------------------------- Reduce -------------------------------------------------- */
    
    /**
     * Reduces the iterable elements to a single element by applying a given function.
     */
    @Pure
    @SuppressWarnings("unchecked")
    protected @Nullable T reduceInternal(@Nonnull BinaryFunction<T, T, T> function) {
        final @Nonnull @NullableElements Iterator<T> iterator = iterator();
        @Nullable T left = null;
        if (iterator.hasNext()) {
            left = iterator.next();
            while (iterator.hasNext()) {
                @Nonnull T right = iterator.next();
                left = function.apply(left, right);
            }
        }
        return left;
    }
    
    /**
     * Reduces the iterable elements to a single element by applying a given function.
     * The result may be null.
     */
    @Pure
    public @Nullable T reduce(@Nonnull NullableToNullableBinaryFunction<T, T, T> function) {
        return reduceInternal(function);
    }
    
    /**
     * Reduces the iterable elements to a single element by applying a given function.
     * The result may not be null.
     */
    @Pure
    public @Nonnull T reduce(@Nonnull NullableToNonNullBinaryFunction<T, T, T> function) {
        @Nullable T reducedValue = reduceInternal(function);
        assert reducedValue != null;
        final @Nonnull T result = reducedValue;
        return result;
    }
    
    /* -------------------------------------------------- Zip -------------------------------------------------- */
    
    /**
     * Zips this iterable with another iterable. An iterable of nullable pairs is returned.
     * If one iterable is shorter, excess elements of the longer iterable are discarded.
     */
    public @Nonnull <T2> NonNullableIterable<NullablePair<T,T2>> zip(@Nonnull NullableIterable<T2> iterable) {
        return new ZipToNullablePairNonNullIterable<>(this, iterable);
    } 
    
    /**
     * Zips this iterable with another iterable. An iterable of nullable pairs is returned.
     * If one iterable is shorter, excess elements of the longer iterable are zipped with null values.
     */
    public @Nonnull <T2> NonNullableIterable<NullablePair<T,T2>> zipLongest(@Nonnull NullableIterable<T2> iterable) {
        return new ZipToNullablePairNonNullIterable<>(this, iterable, ZipStrategy.LONGEST_SEQUENCE);
    } 
    
    /**
     * Zips this iterable with two other iterables. An iterable of nullable triplets is returned.
     * If one or more iterables are shorter, excess elements of the longer iterable are zipped with null values.
     */
    public @Nonnull <T2, T3> NonNullableIterable<NullableTriplet<T,T2, T3>> zipLongest(@Nonnull NullableIterable<T2> iterable2, @Nonnull NullableIterable<T3> iterable3) {
        return new ZipToNullableTripletNonNullIterable<>(this, iterable2, iterable3, ZipStrategy.LONGEST_SEQUENCE);
    } 
    
    /**
     * Zips this iterable with three other iterables. An iterable of nullable quartets is returned. 
     * If one or more iterables are shorter, excess elements of the longer iterable are zipped with null values.
     */
    public @Nonnull <T2, T3, T4> NonNullableIterable<NullableQuartet<T,T2, T3, T4>> zipLongest(@Nonnull NullableIterable<T2> iterable2, @Nonnull NullableIterable<T3> iterable3, @Nonnull NullableIterable<T4> iterable4) {
        return new ZipToNullableQuartetNonNullIterable<>(this, iterable2, iterable3, iterable4, ZipStrategy.LONGEST_SEQUENCE);
    }
    
    /* -------------------------------------------------- Combine -------------------------------------------------- */
    
    /**
     * Combines this iterable with another iterable.
     */
    public @Nonnull NullableIterable<T> combine(@Nonnull NullableIterable<T>... iterables) {
        final @Nonnull @NonNullableElements List<NullableIterable<T>> combiningIterables = new ArrayList<>();
        combiningIterables.add(this);
        for (@Nonnull NullableIterable<T> iterable : iterables) {
            combiningIterables.add(iterable);
        }
        return new CombineNullableIterable<>(combiningIterables);
    }
    
    /* -------------------------------------------------- List -------------------------------------------------- */
    
    /**
     * Returns a list of the elements contained in this iterable.
     * If the iterable is an infinite iterable, an infinite iterable exception is thrown.
     */
    @Pure
    public @Nonnull @NullableElements List<T> toList() {
        int size = size();
        if (size >= 0){
            final @Nonnull @NullableElements List<T> list = new ArrayList<>(size);
            for (@Nullable T element : this) {
                list.add(element);
            }
            return list;
        } else {
            throw InfiniteIterableException.with("An infinite iterable cannot be converted to a finite list.");
        }
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    /**
     * Returns the size of the iterable.
     */
    @Pure
    public abstract int size();
    
    /**
     * Returns true if the size of this iterable is 0.
     */
    @Pure
    public boolean isEmpty() {
        return size() == 0;
    }
    
    /**
     * Returns true if the size of this iterable is -1, which indicates that the iterable is infinitely large.
     */
    @Pure
    public boolean isInfinite() {
        return size() == -1;
    }
    
}
