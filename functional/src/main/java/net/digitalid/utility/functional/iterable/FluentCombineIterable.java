package net.digitalid.utility.functional.iterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.combine.CombineIterator;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.elements.NullableElements;

/**
 * The combine iterable implements an iterable that combines two iterables and returns an iterator that retrieves 
 * elements from the first iterable before returning elements from the second iterable.
 */
class FluentCombineIterable<T> extends FluentIterable<T> {
    
    /**
     * The list of iterables that are combined.
     */
    private final @Nonnull @NonNullableElements List<Iterable<T>> iterables;
    
    /**
     * Creates a fluent combine iterable by combining other iterables.
     */
    FluentCombineIterable(@Nonnull @NullableElements List<Iterable<T>> iterables) {
        this.iterables = iterables;
    }
    
    @Override
    public @Nonnull @NullableElements Iterator<T> iterator() {
        @Nonnull @NonNullableElements List<Iterator<T>> iterators = new ArrayList<>();
        for (@Nonnull Iterable<T> iterable : iterables) {
            iterators.add(iterable.iterator());
        }
        return new CombineIterator<>(iterators);
    }
    
}
