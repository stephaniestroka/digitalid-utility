package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nullable;

import net.digitalid.utility.functional.iterable.map.MapIterator;
import net.digitalid.utility.functional.iterable.map.function.UnaryFunction;
import net.digitalid.utility.functional.iterable.map.function.ToNullableUnaryFunction;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A fluent map iterable implements the iterable that transforms its elements into other elements using a provided function.
 */
@Immutable
class FluentMapIterable<T, I, A> extends FluentIterable<I> {
    
    /**
     * The original iterable.
     */
    private final FluentIterable<T> iterable;
    
    /**
     * The function that is applied to the elements of the original iterable.
     */
    private final UnaryFunction<T, I, A> function;
    
    /**
     * Additional information that might be needed for the transformation.
     */
    private final @Nullable A additionalInformation;
    
    /**
     * Creates a new map iterable with a given fluent iterable and a given function.
     */
    protected FluentMapIterable(FluentIterable<T> iterable, ToNullableUnaryFunction<T, I, A> function, @Nullable A additionalInformation) {
        this.iterable = iterable;
        this.function = function;
        this.additionalInformation = additionalInformation;
    }
    
    @Pure
    @Override
    public @NullableElements Iterator<I> iterator() {
        return new MapIterator<>(iterable.iterator(), function, additionalInformation);
    }
    
}
