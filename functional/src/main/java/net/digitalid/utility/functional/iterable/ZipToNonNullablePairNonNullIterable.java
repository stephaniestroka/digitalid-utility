package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.zip.ZipToNonNullablePairIterator;
import net.digitalid.utility.tuples.NonNullablePair;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * The zip iterable implements an iterable that combines two non-nullable-elements iterables and returns an iterator that returns non-nullable pairs of the elements of the source iterables.
 */
@Immutable
class ZipToNonNullablePairNonNullIterable<T1, T2> extends NonNullIterable<NonNullablePair<T1, T2>> {
    
    /**
     * The first iterable with non-nullable elements.
     */
    private final @Nonnull @NonNullableElements Iterable<T1> iterable1;
    
    /**
     * The second iterable with non-nullable elements.
     */
    private final @Nonnull @NonNullableElements Iterable<T2> iterable2;
    
    /**
     * Creates a zip-to-non-nullable-pair iterable by combining two non-nullable-elements iterables.
     */
    ZipToNonNullablePairNonNullIterable(@Nonnull @NonNullableElements Iterable<T1> iterable1, @Nonnull @NonNullableElements Iterable<T2> iterable2) {
        this.iterable1 = iterable1;
        this.iterable2 = iterable2;
    }
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Iterator<NonNullablePair<T1, T2>> iterator() {
        return new ZipToNonNullablePairIterator<>(iterable1.iterator(), iterable2.iterator());
    }
    
}
