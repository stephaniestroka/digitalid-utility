package net.digitalid.utility.functional;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 *
 */
public abstract class NonNullableIterable<T> extends NullableIterable<T> {
    
    private final @Nonnull @NonNullableElements Iterable<T> iterable;
    
    protected NonNullableIterable() {
        this.iterable = this;
    }
    
    protected NonNullableIterable(@Nonnull @NonNullableElements Iterable<T> iterable) {
        this.iterable = iterable;
    }
    
}
