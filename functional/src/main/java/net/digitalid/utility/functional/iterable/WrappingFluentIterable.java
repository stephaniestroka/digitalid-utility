package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.elements.NullableElements;

/**
 *
 */
class WrappingFluentIterable<T> extends FluentIterable<T> {
    
    private final @Nonnull @NullableElements Iterable<T> iterable;
    
    WrappingFluentIterable(@Nonnull @NullableElements Iterable<T> iterable) {
        this.iterable = iterable;
    }
    
    @Override
    public @Nonnull @NullableElements Iterator<T> iterator() {
        return iterable.iterator();
    }
    
}
