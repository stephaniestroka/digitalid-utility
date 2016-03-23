package net.digitalid.utility.functional.iterable.old;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.filter.FilterIterator;
import net.digitalid.utility.functional.predicate.NullablePredicate;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * The filter iterable implements a iterable that filters its elements using 
 * a given predicate.
 * The filtered elements are nullable.
 */
@Immutable
class FilterNullableIterable<T> extends NullableIterable<T> {
    
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
     * Creates a new filter iterable, which implements a filter with the given predicate on the iterator.
     * The filtered elements are nullable.
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
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    /**
     * Returns 0 if the iterable is empty, -1 if the iterable is infinite or otherwise the number of filtered elements.
     * Note: Compared to other size() functions, this function must iterate through all elements in the iterable. Therefore, it has a runtime complexity of O(n).
     */
    @Override
    public int size() {
        if (iterable.size() > 0) {
            int size = 0;
            final @Nonnull @NullableElements Iterator<T> iterator = iterator();
            while (iterator.hasNext()) {
                iterator.next();
                size++;
            }
            assert size <= iterable.size();
            return size;
        } else {
            return iterable.size();
        }
    }
    
}
