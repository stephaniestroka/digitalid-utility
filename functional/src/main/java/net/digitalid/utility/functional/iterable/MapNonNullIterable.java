package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.iterable.map.MapIterator;
import net.digitalid.utility.functional.iterable.map.function.UnaryFunction;
import net.digitalid.utility.functional.iterable.map.function.ToNonNullUnaryFunction;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A fluent map iterable implements the iterable that transforms its non-null elements into other elements using a provided function.
 */
@Immutable
class MapNonNullIterable<T, I, A> extends NonNullIterable<I> {
    
    /**
     * The original iterable with non-null elements.
     */
    private final @Nonnull @NonNullableElements Iterable<T> iterable;
    
    /**
     * The function that is applied to the elements of the original iterable.
     */
    private final @Nonnull UnaryFunction<? super T, I, A> function;
    
    private final @Nullable A additionalInformation;
    
    /**
     * Creates a new map iterable with a given fluent iterable and a given function.
     */
    protected MapNonNullIterable(@Nonnull @NullableElements NullableIterable<T> iterable, ToNonNullUnaryFunction<? super T, I, A> function, @Nullable A additionalInformation) {
        this.iterable = iterable;
        this.function = function;
        this.additionalInformation = additionalInformation;
    }
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Iterator<I> iterator() {
        return new MapIterator<>(iterable.iterator(), function, additionalInformation);
    }
    
}
