package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.zip.NonNullZipIterator;
import net.digitalid.utility.tuples.NonNullablePair;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * The zip iterable implements an iterable that combines two or more iterables and returns an iterator that alternates the retrieval of the next element.
 */
@Immutable
class ZipNonNullIterable<T1, T2> extends NonNullIterable<NonNullablePair<T1, T2>> {
    
    /**
     * The first iterable.
     */
    private final @Nonnull @NonNullableElements Iterable<T1> iterable1;
    
    /**
     * The second iterable.
     */
    private final @Nonnull @NonNullableElements Iterable<T2> iterable2;
    
    /**
     * Creates a fluent zip iterable by combining other iterables.
     */
    ZipNonNullIterable(@Nonnull @NonNullableElements Iterable<T1> iterable1, @Nonnull @NonNullableElements Iterable<T2> iterable2) {
        this.iterable1 = iterable1;
        this.iterable2 = iterable2;
    }
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Iterator<NonNullablePair<T1, T2>> iterator() {
        return new NonNullZipIterator<>(iterable1.iterator(), iterable2.iterator());
    }
    
}
