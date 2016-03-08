package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * Wraps an ordinary iterable into a fluent iterable.
 */
@Immutable
class WrappingFluentIterable<T> extends FluentIterable<T> {
    
    /**
     * The original iterable.
     */
    private final @Nonnull @NullableElements Iterable<T> iterable;
    
    /**
     * Creates a wrapper around the original iterable.
     */
    WrappingFluentIterable(@Nonnull @NullableElements Iterable<T> iterable) {
        this.iterable = iterable;
    }
    
    @Pure
    @Override
    public @Nonnull @NullableElements Iterator<T> iterator() {
        return iterable.iterator();
    }
    
}
