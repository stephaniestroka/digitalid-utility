package net.digitalid.utility.functional.iterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.combine.CombineIterator;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 * The combine iterable implements an iterable that combines two iterables and returns an iterator that retrieves 
 * elements from the first iterable before returning elements from the second iterable.
 */
class CombineNonNullIterable<T> extends NonNullIterable<T> {
    
    /**
     * The list of iterables that are combined.
     */
    private final @Nonnull @NonNullableElements List<NonNullIterable<T>> iterables;
    
    /**
     * Creates a fluent combine iterable by combining other iterables.
     */
    CombineNonNullIterable(@Nonnull @NonNullableElements List<NonNullIterable<T>> iterables) {
        this.iterables = iterables;
    }
    
    @Override
    public @Nonnull @NonNullableElements Iterator<T> iterator() {
        @Nonnull @NonNullableElements List<Iterator<T>> iterators = new ArrayList<>();
        for (@Nonnull Iterable<T> iterable : iterables) {
            iterators.add(iterable.iterator());
        }
        return new CombineIterator<>(iterators);
    }
    
    @Override
    public int size() {
        int size = 0;
        for (NonNullIterable<T> iterable : iterables) {
            size += iterable.size();
        }
        return size;
    }
    
}
