package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.filter.FilterIterator;
import net.digitalid.utility.functional.predicate.NullablePredicate;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * The fluent filter iterable implements a fluent iterable that filters its elements using 
 * a given predicate.
 */
@Immutable
class FilterNullableIterable<T> extends NullableIterable<T> {
    
    /* -------------------------------------------------- Final Fields -------------------------------------------------- */
    
    /**
     * The predicate which is used to filter elements from an iterator. Only the elements that
     * satisfy the predicate are returned by this iterator.
     */
    private final @Nonnull NullablePredicate<T> predicate;
    
    /**
     * The iterator which serves as a source for the elements.
     */
    private final @Nonnull @NullableElements NullableIterable<T> iterable;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new fluent filter iterable, which implements a filter with the given predicate on the iterator.
     */
    protected FilterNullableIterable(@Nonnull @NullableElements NullableIterable<T> iterable, @Nonnull NullablePredicate<T> predicate) {
        this.iterable = iterable;
        this.predicate = predicate;
    }
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    @Override
    public @Nonnull @NullableElements Iterator<T> iterator() {
        return new FilterIterator<>(iterable.iterator(), predicate);
    }
    
    // TODO: The infinite iterables must override the filter() method of the super class, such that they can return -1 (= infinite) or 0 for the size.
    @Override
    public int size() {
        int size = 0;
        final @Nonnull @NullableElements Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            iterator.next();
            size++;
        }
        return size;
    }
    
}
