package net.digitalid.utility.functional.iterable.old;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.combine.CombineIterator;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 * The combine iterable implements an iterable that combines two or more iterables with non-nullable elements and returns an iterator that retrieves 
 * elements from the iterables in sequence.
 */
class CombineNonNullIterable<T> extends NonNullableIterable<T> {
    
    /**
     * The list of iterables that are combined.
     */
    private final @Nonnull @NonNullableElements List<NonNullableIterable<T>> iterables;
    
    /**
     * Creates a combine iterable by combining other iterables.
     */
    CombineNonNullIterable(@Nonnull @NonNullableElements List<NonNullableIterable<T>> iterables) {
        this.iterables = iterables;
    }
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    @Override
    public @Nonnull @NonNullableElements Iterator<T> iterator() {
        @Nonnull @NonNullableElements List<Iterator<T>> iterators = new ArrayList<>();
        for (@Nonnull Iterable<T> iterable : iterables) {
            iterators.add(iterable.iterator());
        }
        return new CombineIterator<>(iterators);
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Override
    public int size() {
        int size = 0;
        for (NonNullableIterable<T> iterable : iterables) {
            size += iterable.size();
        }
        return size;
    }
    
}
