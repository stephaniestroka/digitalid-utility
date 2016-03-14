package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.iterable.map.MapIterator;
import net.digitalid.utility.functional.function.unary.UnaryFunction;
import net.digitalid.utility.functional.function.unary.ToNonNullUnaryFunction;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A fluent map iterable implements the iterable that transforms its non-null elements into other elements using a provided function.
 */
@Immutable
class MapNonNullIterable<T, I> extends NonNullIterable<I> {
    
    /**
     * The original iterable with non-null elements.
     */
    private final @Nonnull @NullableElements NullableIterable<T> iterable;
    
    /**
     * The function that is applied to the elements of the original iterable.
     */
    private final @Nonnull UnaryFunction<? super T, I> function;
    
    /**
     * Creates a new map iterable with a given fluent iterable and a given function.
     */
    protected MapNonNullIterable(@Nonnull @NullableElements NullableIterable<T> iterable, ToNonNullUnaryFunction<? super T, I> function) {
        this.iterable = iterable;
        this.function = function;
    }
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Iterator<I> iterator() {
        return new MapIterator<>(iterable.iterator(), function);
    }
    
    @Override
    public int size() {
        return iterable.size();
    }
    
}
