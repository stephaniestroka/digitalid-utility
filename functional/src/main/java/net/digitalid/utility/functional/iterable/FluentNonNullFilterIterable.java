package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.filter.FilterIterator;
import net.digitalid.utility.functional.iterable.filter.predicate.NonNullPredicate;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * The fluent filter iterable implements a fluent iterable that filters its elements using 
 * a given predicate.
 * The filtered elements are non-nullable.
 */
@Immutable
class FluentNonNullFilterIterable<T> extends FluentNonNullIterable<T> {
    
    /**
     * The iterator which serves as a source for the elements.
     */
    private final @Nonnull @NullableElements FluentIterable<T> iterable;
    
    /**
     * The predicate which is used to filter elements from an iterator. Only the elements that
     * satisfy the predicate are returned by this iterator.
     */
    private final @Nonnull NonNullPredicate<T> predicate;
    
    /**
     * Creates a new fluent filter iterable, which implements a filter with the given predicate on the iterator.
     * The filtered elements are non-nullable.
     */
    protected FluentNonNullFilterIterable(@Nonnull @NullableElements FluentIterable<T> iterable, @Nonnull NonNullPredicate<T> predicate) {
        this.iterable = iterable;
        this.predicate = predicate;
    }
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Iterator<T> iterator() {
        return new FilterIterator<>(iterable.iterator(), predicate);
    }
    
}
