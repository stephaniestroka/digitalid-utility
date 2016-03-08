package net.digitalid.utility.functional.iterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.zip.ZipIterator;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * The zip iterable implements an iterable that combines two or more iterables and returns an iterator that alternates the retrieval of the next element.
 */
@Immutable
class FluentZipIterable<T> extends FluentIterable<T> {
    
    /**
     * The list of iterables.
     */
    private final @Nonnull @NullableElements List<Iterable<T>> iterables;
    
    /**
     * Creates a fluent zip iterable by combining other iterables.
     */
    FluentZipIterable(@Nonnull @NullableElements List<Iterable<T>> iterables) {
        this.iterables = iterables;
    }
    
    @Pure
    @Override
    public @Nonnull @NullableElements Iterator<T> iterator() {
        @Nonnull @NonNullableElements List<Iterator<T>> iterators = new ArrayList<>();
        for (@Nonnull Iterable<T> iterable : iterables) {
            iterators.add(iterable.iterator());
        }
        return new ZipIterator<>(iterators);
    }
    
}
