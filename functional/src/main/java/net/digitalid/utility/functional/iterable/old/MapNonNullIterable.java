package net.digitalid.utility.functional.iterable.old;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.function.unary.ToNonNullUnaryFunction;
import net.digitalid.utility.functional.iterable.map.MapIterator;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A map iterable implements the iterable that transforms its elements into other elements using a provided function.
 * The applied function guarantees that the elements of the original iterable are only transformed into non-nullable elements.
 */
@Immutable
class MapNonNullIterable<T, I> extends NonNullableIterable<I> {
    
    /**
     * The original iterable with nullable elements.
     */
    private final @Nonnull NullableIterable<T> iterable;
    
    /**
     * The function that is applied to the elements of the original iterable.
     */
    private final @Nonnull ToNonNullUnaryFunction<? super T, I> function;
    
    /**
     * Creates a new map iterable with a given iterable and a given function.
     */
    protected MapNonNullIterable(@Nonnull NullableIterable<T> iterable, @Nonnull ToNonNullUnaryFunction<? super T, I> function) {
        this.iterable = iterable;
        this.function = function;
    }
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Iterator<I> iterator() {
        return new MapIterator<>(iterable.iterator(), function);
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Pure
    @Override
    public int size() {
        return iterable.size();
    }
    
}
