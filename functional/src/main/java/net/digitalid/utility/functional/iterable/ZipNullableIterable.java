package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.zip.NullableZipIterator;
import net.digitalid.utility.tuples.NullablePair;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * The zip iterable implements an iterable that combines two or more iterables and returns an iterator that alternates the retrieval of the next element.
 */
@Immutable
class ZipNullableIterable<T1, T2> extends NullableIterable<NullablePair<T1, T2>> {
    
    /**
     * The first iterable.
     */
    private final @Nonnull @NullableElements Iterable<T1> iterable1;
    
    /**
     * The second iterable.
     */
    private final @Nonnull @NullableElements Iterable<T2> iterable2;
    
    /**
     * Creates a fluent zip iterable by combining other iterables.
     */
    ZipNullableIterable(@Nonnull @NullableElements Iterable<T1> iterable1, @Nonnull @NullableElements Iterable<T2> iterable2) {
        this.iterable1 = iterable1;
        this.iterable2 = iterable2;
    }
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Iterator<NullablePair<T1, T2>> iterator() {
        return new NullableZipIterator<>(iterable1.iterator(), iterable2.iterator());
    }
    
}
