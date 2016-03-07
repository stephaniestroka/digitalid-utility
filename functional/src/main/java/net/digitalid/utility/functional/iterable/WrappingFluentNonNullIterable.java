package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 *
 */
@Immutable
class WrappingFluentNonNullIterable<T> extends FluentNonNullIterable<T> {
    
    private final @Nonnull @NonNullableElements Iterable<T> iterable;
    
    WrappingFluentNonNullIterable(@Nonnull @NonNullableElements Iterable<T> iterable) {
        this.iterable = iterable;
    }
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Iterator<T> iterator() {
        return iterable.iterator();
    }
    
}
