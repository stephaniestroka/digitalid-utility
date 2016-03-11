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
class WrappingNonNullIterable<T> extends NonNullIterable<T> {
    
    private final @Nonnull @NonNullableElements Iterable<T> iterable;
    
    WrappingNonNullIterable(@Nonnull @NonNullableElements Iterable<T> iterable) {
        this.iterable = iterable;
    }
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Iterator<T> iterator() {
        return iterable.iterator();
    }
    
}
