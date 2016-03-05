package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import net.digitalid.utility.functional.iterable.map.MapIterator;
import net.digitalid.utility.functional.iterable.map.function.Function;
import net.digitalid.utility.validation.annotations.elements.NullableElements;

/**
 *
 */
class FluentMapIterable<T, I> extends FluentIterable<I> {
    
    private final Iterable<T> iterable;
    private final Function<T, I> function;
    
    protected FluentMapIterable(Iterable<T> iterable, Function<T, I> function) {
        this.iterable = iterable;
        this.function = function;
    }
    
    @Override
    public @NullableElements Iterator<I> iterator() {
        return new MapIterator<>(iterable.iterator(), function);
    }
    
}
