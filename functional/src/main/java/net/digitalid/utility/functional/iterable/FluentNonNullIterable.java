package net.digitalid.utility.functional.iterable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.iterable.filter.predicate.NullablePredicate;
import net.digitalid.utility.functional.iterable.map.function.NonNullToNonNullFunction;
import net.digitalid.utility.functional.iterable.map.function.NonNullToNullableFunction;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * The fluent non-null iterable has the same properties as the fluent iterable, but only
 * holds and retrieves non-nullable elements.
 */
@Stateless
abstract class FluentNonNullIterable<T> extends FluentIterable<T> {
    
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
    public @Nonnull @NonNullableElements FluentNonNullIterable<T> filter(@Nonnull NullablePredicate<T> predicate) {
        return new FluentNonNullFilterIterable<>(this, predicate);
    }
    
    /* -------------------------------------------------- Map -------------------------------------------------- */
    
    /**
     * Takes a non-null iterable and maps its elements to a new iterable by passing each element through a given function.
     * The function maps non-null elements to non-null elements, therefore the resulting iterable
     * contains only non-nullable elements.
     */
    public @Nonnull @NonNullableElements <E> FluentNonNullIterable<E> map(@Nonnull NonNullToNonNullFunction<T, E> function) {
        return new FluentNonNullMapIterable<>(this, function);
    }
    
    /**
     * Takes a non-null iterable and maps its elements to a new iterable by passing each element through a given function.
     * The function maps non-null elements to nullable elements, therefore the resulting iterable
     * contains nullable elements.
     */
    public @Nonnull @NullableElements <E> FluentIterable<E> map(@Nonnull NonNullToNullableFunction<T, E> function) {
        return new FluentMapIterable<>(this, function);
    }
    
    /* -------------------------------------------------- Reduce -------------------------------------------------- */
    
    /**
     * Returns a value of type T which is calculated using the given function.
     * The function expects two values: The first value is the output of the previous calculation, 
     * or an empty element if we are at the beginning, and the second is the next element in the iterable.
     * The return value might be null.
     */
    public @Nullable T reduce(@Nonnull NonNullToNullableFunction<T, T> function) {
        return reduceInternal(function);
    }
    
    /**
     * Returns a value of type T which is calculated using the given function.
     * The function expects two values: The first value is the output of the previous calculation, 
     * or an empty element if we are at the beginning, and the second is the next element in the iterable.
     * The return value cannot be null.
     */
    public @Nonnull T reduce(@Nonnull NonNullToNonNullFunction<T, T> function) {
        @Nullable T reducedValue = reduceInternal(function);
        assert reducedValue != null;
        final @Nonnull T result = reducedValue;
        return result;
    }
    
    // TODO: implements zip and combine. The only difference to the FluentIterable.zip() and FluentIterable.combine() is that the FluentNonNullIterable may return a FluentNonNullIterable if the given iterables are also non-null iterables.
}
