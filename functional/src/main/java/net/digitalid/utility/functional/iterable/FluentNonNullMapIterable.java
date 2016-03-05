package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.map.MapIterator;
import net.digitalid.utility.functional.iterable.map.function.Function;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 *
 */
class FluentNonNullMapIterable<T, I> extends FluentNonNullIterable<I> {
    
    private final @Nonnull @NonNullableElements Iterable<T> iterable;
    private final Function<T, I> function;
    
    protected FluentNonNullMapIterable(@Nonnull @NonNullableElements Iterable<T> iterable, Function<T, I> function) {
        this.iterable = iterable;
        this.function = function;
    }
    
    @Override
    public @Nonnull @NonNullableElements Iterator<I> iterator() {
        return new MapIterator<>(iterable.iterator(), function);
    }
    
}
