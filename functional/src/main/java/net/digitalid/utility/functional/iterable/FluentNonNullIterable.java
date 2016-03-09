package net.digitalid.utility.functional.iterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.iterable.exceptions.UnexpectedResultException;
import net.digitalid.utility.functional.iterable.filter.predicate.NonNullPredicate;
import net.digitalid.utility.functional.iterable.map.function.NonNullToNonNullUnaryFunction;
import net.digitalid.utility.functional.iterable.map.function.NonNullToNullableUnaryFunction;
import net.digitalid.utility.functional.iterable.zip.function.NonNullToNonNullBinaryFunction;
import net.digitalid.utility.functional.iterable.zip.function.NonNullToNullableBinaryFunction;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * The fluent non-null iterable has the same properties as the fluent iterable, but only
 * holds and retrieves non-nullable elements.
 */
@Stateless
public abstract class FluentNonNullIterable<T> extends FluentIterable<T> {
    
    /* -------------------------------------------------- Filter -------------------------------------------------- */
    
    /**
     * Returns an iterable which does not have any null elements.
     * Since we have a non-null iterable, this function is a no-op and returns itself.
     */
    @Override
    public @Nonnull @NonNullableElements FluentNonNullIterable<T> filterNonNull() {
        return this;
    }
    
    /**
     * Applies a filter to a non-null iterable and returns a new iterable.
     */
    public @Nonnull @NonNullableElements FluentNonNullIterable<T> filter(@Nonnull NonNullPredicate<T, Object> predicate) {
        return new FluentNonNullFilterIterable<>(this, predicate, null);
    }
    
    /**
     * Applies a filter to a non-null iterable and returns a new iterable.
     */
    public @Nonnull @NonNullableElements <A> FluentNonNullIterable<T> filter(@Nonnull NonNullPredicate<T, A> predicate, @Nullable A additionalInformation) {
        return new FluentNonNullFilterIterable<>(this, predicate, additionalInformation);
    }
    
    /* -------------------------------------------------- Find -------------------------------------------------- */
    
    public @Nullable T find(@Nonnull NonNullPredicate<T, ?> predicate) throws UnexpectedResultException {
        return find(predicate, null);
    }
    
    public @Nullable <A> T find(@Nonnull NonNullPredicate<T, A> predicate, @Nullable A additionalInformation) throws UnexpectedResultException {
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
     * Takes a non-null iterable and maps its elements to a new iterable by passing each element through a given function.
     * The function maps non-null elements to non-null elements, therefore the resulting iterable
     * contains only non-nullable elements.
     */
    public @Nonnull @NonNullableElements <E> FluentNonNullIterable<E> map(@Nonnull NonNullToNonNullUnaryFunction<? super T, E, ?> function) {
        return new FluentNonNullMapIterable<>(this, function, null);
    }
    
    /**
     * Takes a non-null iterable and maps its elements to a new iterable by passing each element through a given function.
     * The function maps non-null elements to nullable elements, therefore the resulting iterable
     * contains nullable elements.
     */
    public @Nonnull @NullableElements <E> FluentIterable<E> map(@Nonnull NonNullToNullableUnaryFunction<T, E, ?> function) {
        return new FluentMapIterable<>(this, function, null);
    }
    
    /**
     * Takes a non-null iterable and maps its elements to a new iterable by passing each element through a given function.
     * The function maps non-null elements to non-null elements, therefore the resulting iterable
     * contains only non-nullable elements.
     */   
    public @Nonnull @NonNullableElements <E, A> FluentNonNullIterable<E> map(@Nonnull NonNullToNonNullUnaryFunction<T, E, A> function, @Nullable A additionalObject) {
        return new FluentNonNullMapIterable<>(this, function, additionalObject);
    }
    
    /**
     * Takes a non-null iterable and maps its elements to a new iterable by passing each element through a given function.
     * The function maps non-null elements to nullable elements, therefore the resulting iterable
     * contains nullable elements.
     */
    public @Nonnull @NullableElements <E, A> FluentIterable<E> map(@Nonnull NonNullToNullableUnaryFunction<T, E, A> function, @Nullable A additionalObject) {
        return new FluentMapIterable<>(this, function, additionalObject);
    }
    
    /* -------------------------------------------------- Reduce -------------------------------------------------- */
    
    /**
     * Returns a value of type T which is calculated using the given function.
     * The function expects two values: The first value is the output of the previous calculation, 
     * or an empty element if we are at the beginning, and the second is the next element in the iterable.
     * The return value might be null.
     */
    public @Nullable T reduce(@Nonnull NonNullToNullableBinaryFunction<T, T> function) {
        return reduceInternal(function);
    }
    
    /**
     * Returns a value of type T which is calculated using the given function.
     * The function expects two values: The first value is the output of the previous calculation, 
     * or an empty element if we are at the beginning, and the second is the next element in the iterable.
     * The return value cannot be null.
     */
    public @Nonnull T reduce(@Nonnull NonNullToNonNullBinaryFunction<T, T> function) {
        @Nullable T reducedValue = reduceInternal(function);
        assert reducedValue != null;
        final @Nonnull T result = reducedValue;
        return result;
    }
    
    /* -------------------------------------------------- Combine -------------------------------------------------- */
    
    /**
     * Combines this iterable with another iterable.
     */
    public @Nonnull @NonNullableElements FluentNonNullIterable<T> combine(@Nonnull Iterable<T>... iterables) {
        final @Nonnull @NonNullableElements List<Iterable<T>> combiningIterables = new ArrayList<>();
        combiningIterables.add(this);
        for (@Nonnull Iterable<T> iterable : iterables) {
            combiningIterables.add(iterable);
        }
        return new FluentNonNullCombineIterable<>(combiningIterables);
    }
    // TODO: implements zip and combine. The only difference to the FluentIterable.zip() and FluentIterable.combine() is that the FluentNonNullIterable may return a FluentNonNullIterable if the given iterables are also non-null iterables.
    
    public @Nonnull @NonNullableElements List<T> toList() {
        final @Nonnull @NonNullableElements List<T> list = new ArrayList<>();
        for (@Nonnull T element : this) {
            list.add(element);
        }
        return list;
    }
    
}
