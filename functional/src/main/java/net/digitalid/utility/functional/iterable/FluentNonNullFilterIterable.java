package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.filter.FilterIterator;
import net.digitalid.utility.functional.iterable.filter.predicate.NonNullPredicate;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.elements.NullableElements;

/**
 *
 */
class FluentNonNullFilterIterable<T> extends FluentNonNullIterable<T> {
    
    private final @Nonnull @NullableElements Iterable<T> iterable;
    private final @Nonnull NonNullPredicate<T> predicate;
    
    protected FluentNonNullFilterIterable(@Nonnull @NullableElements Iterable<T> iterable, @Nonnull NonNullPredicate<T> predicate) {
        this.iterable = iterable;
        this.predicate = predicate;
    }
    
    @Override
    public @Nonnull @NonNullableElements Iterator<T> iterator() {
        return new FilterIterator<>(iterable.iterator(), predicate);
    }
    
}
