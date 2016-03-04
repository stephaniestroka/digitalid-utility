package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.iterable.filter.predicate.FilterNonNullPredicate;
import net.digitalid.utility.functional.iterable.filter.predicate.NonNullPredicate;
import net.digitalid.utility.functional.iterable.filter.predicate.NullablePredicate;
import net.digitalid.utility.functional.iterable.map.function.Function;
import net.digitalid.utility.functional.iterable.map.function.NullableToNonNullFunction;
import net.digitalid.utility.functional.iterable.map.function.NullableToNullableFunction;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.elements.NullableElements;

/**
 *
 */
public abstract class FluentIterable<T> implements Iterable<T> {
    
    /* -------------------------------------------------- Wrapping -------------------------------------------------- */
    
    public static @Nonnull @NonNullableElements <T> FluentNonNullIterable<T> ofNonNullElements(@Nonnull @NonNullableElements Iterable<T> iterable) {
        return new WrappingFluentNonNullIterable<>(iterable);
    }
    
    public static @Nonnull @NullableElements <T> FluentIterable<T> of(@Nonnull @NullableElements Iterable<T> iterable) {
        return new WrappingFluentIterable<>(iterable);
    }
    
    /* -------------------------------------------------- Filter -------------------------------------------------- */
    
    // TODO: implement this:
    // Ideally, this should only be visible for FluentIterable.
/*    public @Nonnull @NonNullableElements FluentNonNullIterable<T> filterNonNull() {
        return new FluentNonNullFilterIterable<>(this, new FilterNonNullPredicate<>());
    }
*/    
    public @Nonnull @NullableElements FluentIterable<T> filter(@Nonnull NullablePredicate<T> predicate) {
        return new FluentFilterIterable<>(this, predicate);
    }
    
    public @Nonnull @NonNullableElements <E> FluentNonNullIterable<E> map(@Nonnull NullableToNonNullFunction<T, E> function) {
        return new FluentNonNullMapIterable<>(this, function);
    }
    
    public @Nonnull @NullableElements <E> FluentIterable<E> map(@Nonnull NullableToNullableFunction<T, E> function) {
        return new FluentMapIterable<>(this, function);
    }
    
    /* -------------------------------------------------- Reduce -------------------------------------------------- */
    
    @SuppressWarnings("unchecked")
    protected @Nullable T reduceInternal(@Nonnull Function<T, T> function) {
        final @Nonnull @NullableElements Iterator<T> iterator = iterator();
        @Nullable T first = null;
        if (iterator.hasNext()) {
            first = iterator.next();
            while (iterator.hasNext()) {
                @Nonnull T second = iterator.next();
                first = function.apply(first, second);
            }
        }
        return first;
    }
    
    /* -------------------------------------------------- Reduce -------------------------------------------------- */
    
    public @Nullable T reduce(@Nonnull NullableToNullableFunction<T, T> function) {
        return reduceInternal(function);
    }
    
    public @Nonnull T reduce(@Nonnull NullableToNonNullFunction<T, T> function) {
        @Nullable T reducedValue = reduceInternal(function);
        assert reducedValue != null;
        final @Nonnull T result = reducedValue;
        return result;
    }
    
}
