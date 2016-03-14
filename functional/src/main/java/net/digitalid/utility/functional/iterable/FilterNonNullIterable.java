package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.iterable.filter.FilterIterator;
import net.digitalid.utility.functional.iterable.filter.predicate.implementation.FilterNonNullPredicate;
import net.digitalid.utility.functional.predicate.NonNullPredicate;
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
class FilterNonNullIterable<T> extends NonNullIterable<T> {
    
    /**
     * The iterator which serves as a source for the elements.
     */
    private final @Nonnull @NullableElements NullableIterable<T> iterable;
    
    /**
     * The predicate which is used to filter elements from an iterator. Only the elements that
     * satisfy the predicate are returned by this iterator.
     */
    private final @Nonnull NonNullPredicate<T> predicate;
    
    /**
     * Creates a new fluent filter iterable, which implements a filter with the given predicate on the iterator.
     * The filtered elements are non-nullable.
     */
    protected FilterNonNullIterable(@Nonnull @NonNullableElements NonNullIterable<T> iterable, @Nonnull NonNullPredicate<T> predicate) {
        this.iterable = iterable;
        this.predicate = predicate;
    }
    
    /**
     * Creates a new fluent filter iterable with non-null elements on a fluent iterable with potential nullable elements and a predicate that filters non-null elements.
     */
    protected FilterNonNullIterable(@Nonnull @NonNullableElements NullableIterable<T> iterable, @Nonnull FilterNonNullPredicate<T> predicate) {
        this.iterable = iterable;
        this.predicate = predicate;
    }
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Iterator<T> iterator() {
        return new FilterIterator<>(iterable.iterator(), predicate);
    }
    
    // TODO: The infinite iterables must override the filter() method of the super class, such that they can return -1 (= infinite) or 0 for the size.
    @Override
    public int size() {
        int size = 0;
        final @Nonnull @NonNullableElements Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            iterator.next();
            size++;
        }
        return size;
    }
    
}
