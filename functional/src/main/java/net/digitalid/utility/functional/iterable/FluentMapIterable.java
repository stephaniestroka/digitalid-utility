package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import net.digitalid.utility.functional.iterable.map.MapIterator;
import net.digitalid.utility.functional.iterable.map.function.Function;
import net.digitalid.utility.functional.iterable.map.function.ToNullableFunction;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A fluent map iterable implements the iterable that transforms its elements into other elements using a provided function.
 */
@Immutable
class FluentMapIterable<T, I> extends FluentIterable<I> {
    
    /**
     * The original iterable.
     */
    private final FluentIterable<T> iterable;
    
    /**
     * The function that is applied to the elements of the original iterable.
     */
    private final Function<T, I> function;
    
    /**
     * Creates a new map iterable with a given fluent iterable and a given function.
     */
    protected FluentMapIterable(FluentIterable<T> iterable, ToNullableFunction<T, I> function) {
        this.iterable = iterable;
        this.function = function;
    }
    
    @Pure
    @Override
    public @NullableElements Iterator<I> iterator() {
        return new MapIterator<>(iterable.iterator(), function);
    }
    
}
