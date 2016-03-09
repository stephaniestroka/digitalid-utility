package net.digitalid.utility.functional.iterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.iterable.exceptions.UnexpectedResultException;
import net.digitalid.utility.functional.iterable.filter.predicate.FilterNonNullPredicate;
import net.digitalid.utility.functional.iterable.filter.predicate.NullablePredicate;
import net.digitalid.utility.functional.iterable.map.function.NullableToNonNullUnaryFunction;
import net.digitalid.utility.functional.iterable.map.function.NullableToNullableUnaryFunction;
import net.digitalid.utility.functional.iterable.zip.function.BinaryFunction;
import net.digitalid.utility.functional.iterable.zip.function.NullableToNonNullBinaryFunction;
import net.digitalid.utility.functional.iterable.zip.function.NullableToNullableBinaryFunction;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * This class represents an iterable to which elements filtering and transformation functions
 * can be applied.
 */
@Stateless
public abstract class FluentIterable<T> implements Iterable<T> {
    
    /* -------------------------------------------------- Wrapping -------------------------------------------------- */
    
    /**
     * Returns a new non-nullable elements iterable.
     */
    public static @Nonnull @NonNullableElements <T> FluentNonNullIterable<T> ofNonNullElements(@Nonnull @NonNullableElements Iterable<T> iterable) {
        return new WrappingFluentNonNullIterable<>(iterable);
    }
    
    /**
     * Returns a new iterable that may contain null elements.
     */
    public static @Nonnull @NullableElements <T> FluentIterable<T> of(@Nonnull @NullableElements Iterable<T> iterable) {
        return new WrappingFluentIterable<>(iterable);
    }
    
    /* -------------------------------------------------- Filter -------------------------------------------------- */
    
    /**
     * Filters all elements that are non-null and returns a fluent iterable with non-null elements.
     */
    // Ideally, this should only be visible for FluentIterable.
    public @Nonnull @NonNullableElements FluentNonNullIterable<T> filterNonNull() {
        return new FluentNonNullFilterIterable<>(this, new FilterNonNullPredicate<T, Object>(), null);
    }
    
    /**
     * Filters the elements of a fluent iterable using a given predicate.
     */
    public @Nonnull @NullableElements FluentIterable<T> filter(@Nonnull NullablePredicate<T, ?> predicate) {
        return new FluentFilterIterable<>(this, predicate, null);
    }
    
    /**
     * Filters the elements of a fluent iterable using a given predicate.
     */
    public @Nonnull @NullableElements <A> FluentIterable<T> filter(@Nonnull NullablePredicate<T, A> predicate, @Nullable A additionalInformation) {
        return new FluentFilterIterable<>(this, predicate, additionalInformation);
    }
    
    /* -------------------------------------------------- Find -------------------------------------------------- */
    
    public @Nullable T find(@Nonnull NullablePredicate<T, ?> predicate) throws UnexpectedResultException {
        return find(predicate, null);
    }
    
    public @Nullable <A> T find(@Nonnull NullablePredicate<T, A> predicate, @Nullable A additionalInformation) throws UnexpectedResultException {
        final @Nonnull @NullableElements FluentIterable<T> iterable = filter(predicate, additionalInformation);
        final @Nonnull @NullableElements Iterator<T> iterator = iterable.iterator();
        if (!iterator.hasNext()) {
            return null;
        } else {
            final @Nullable T element = iterator.next();
            if (iterator.hasNext()) {
                throw UnexpectedResultException.with("Found more than one elements for the given predicate");
            }
            return element;
        }
    }
    
    /* -------------------------------------------------- Map -------------------------------------------------- */
    
    /**
     * Maps the elements of this iterable to elements that are non-null, using a given function.
     */
    public @Nonnull @NonNullableElements <E> FluentNonNullIterable<E> map(@Nonnull NullableToNonNullUnaryFunction<T, E, ?> function) {
        return new FluentNonNullMapIterable<>(this, function, null);
    }
    
    /**
     * Maps the elements of this iterable to elements that are nullable, using a given function.
     */
    public @Nonnull @NullableElements <E> FluentIterable<E> map(@Nonnull NullableToNullableUnaryFunction<T, E, ?> function) {
        return new FluentMapIterable<>(this, function, null);
    }
    
        /**
     * Maps the elements of this iterable to elements that are non-null, using a given function.
     */
    public @Nonnull @NonNullableElements <E, A> FluentNonNullIterable<E> map(@Nonnull NullableToNonNullUnaryFunction<T, E, A> function, A additionalInformation) {
        return new FluentNonNullMapIterable<>(this, function, additionalInformation);
    }
    
    /**
     * Maps the elements of this iterable to elements that are nullable, using a given function.
     */
    public @Nonnull @NullableElements <E, A> FluentIterable<E> map(@Nonnull NullableToNullableUnaryFunction<T, E, A> function, A additionalInformation) {
        return new FluentMapIterable<>(this, function, additionalInformation);
    }
    
    /* -------------------------------------------------- Reduce -------------------------------------------------- */
    
    /**
     * Reduces the iterable elements to a single element by applying a given function.
     */
    @SuppressWarnings("unchecked")
    // TODO: introduce binaryFunction
    protected @Nullable T reduceInternal(@Nonnull BinaryFunction<T, T> function) {
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
    public @Nullable T reduce(@Nonnull NullableToNullableBinaryFunction<T, T> function) {
        return reduceInternal(function);
    }
    
    /**
     * Reduces the iterable elements to a single element by applying a given function.
     * The result may not be null.
     */
    public @Nonnull T reduce(@Nonnull NullableToNonNullBinaryFunction<T, T> function) {
        @Nullable T reducedValue = reduceInternal(function);
        assert reducedValue != null;
        final @Nonnull T result = reducedValue;
        return result;
    }
    
    /* -------------------------------------------------- Zip -------------------------------------------------- */
    
    /**
     * Zips this iterable with another iterable.
     */
    public @Nonnull @NullableElements FluentIterable<T> zip(@Nonnull Iterable<T>... iterables) {
        final @Nonnull @NonNullableElements List<Iterable<T>> combiningIterables = new ArrayList<>();
        combiningIterables.add(this);
        for (@Nonnull Iterable<T> iterable : iterables) {
            combiningIterables.add(iterable);
        }
        return new FluentZipIterable<>(combiningIterables);
    } 
    
    /* -------------------------------------------------- Combine -------------------------------------------------- */
    
    /**
     * Combines this iterable with another iterable.
     */
    public @Nonnull @NullableElements FluentIterable<T> combine(@Nonnull Iterable<T>... iterables) {
        final @Nonnull @NonNullableElements List<Iterable<T>> combiningIterables = new ArrayList<>();
        combiningIterables.add(this);
        for (@Nonnull Iterable<T> iterable : iterables) {
            combiningIterables.add(iterable);
        }
        return new FluentCombineIterable<>(combiningIterables);
    }
    
    /* -------------------------------------------------- List -------------------------------------------------- */
    
    public @Nonnull @NullableElements List<T> toList() {
        final @Nonnull @NullableElements List<T> list = new ArrayList<>();
        for (@Nullable T element : this) {
            list.add(element);
        }
        return list;
    }
    
}
