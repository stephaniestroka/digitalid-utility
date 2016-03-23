package net.digitalid.utility.functional.iterable.old;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.function.unary.ToNullableUnaryFunction;
import net.digitalid.utility.functional.iterable.map.MapIterator;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A map iterable implements the iterable that transforms its elements into other elements using a provided function.
 * The applied function cannot guarantee that the elements of the original iterable are only transformed into non-nullable elements. 
 * In other words, the resulting iterable might contain null values.
 */
@Immutable
class MapNullableIterable<T, I> extends NullableIterable<I> {
    
    /**
     * The original iterable with nullable elements.
     */
    private final NullableIterable<T> iterable;
    
    /**
     * The function that is applied to the elements of the original iterable.
     */
    private final ToNullableUnaryFunction<T, I> function;
    
    /**
     * Creates a new map iterable with a given iterable and a given function.
     */
    protected MapNullableIterable(@Nonnull NullableIterable<T> iterable, ToNullableUnaryFunction<T, I> function) {
        this.iterable = iterable;
        this.function = function;
    }
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    @Pure
    @Override
    public @NullableElements Iterator<I> iterator() {
        return new MapIterator<>(iterable.iterator(), function);
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Override
    public int size() {
        return iterable.size();
    }
    
}
