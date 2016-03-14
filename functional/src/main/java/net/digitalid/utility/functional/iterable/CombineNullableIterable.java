package net.digitalid.utility.functional.iterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.combine.CombineIterator;
import net.digitalid.utility.validation.annotations.elements.NullableElements;

/**
 * The combine iterable implements an iterable that combines two iterables and returns an iterator that retrieves 
 * elements from the first iterable before returning elements from the second iterable.
 */
class CombineNullableIterable<T> extends NullableIterable<T> {
    
    /**
     * The list of iterables that are combined.
     */
    private final @Nonnull @NullableElements List<NullableIterable<T>> iterables;
    
    /**
     * Creates a fluent combine iterable by combining other iterables.
     */
    CombineNullableIterable(@Nonnull @NullableElements List<NullableIterable<T>> iterables) {
        this.iterables = iterables;
    }
    
    @Override
    public @Nonnull @NullableElements Iterator<T> iterator() {
        @Nonnull @NullableElements List<Iterator<T>> iterators = new ArrayList<>();
        for (@Nonnull Iterable<T> iterable : iterables) {
            iterators.add(iterable.iterator());
        }
        return new CombineIterator<>(iterators);
    }
    
    @Override
    public int size() {
        int size = 0;
        for (NullableIterable<T> iterable : iterables) {
            size += iterable.size();
        }
        return size;
    }
    
}
