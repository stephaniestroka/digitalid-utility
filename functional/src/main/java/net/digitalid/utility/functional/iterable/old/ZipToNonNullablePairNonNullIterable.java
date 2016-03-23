package net.digitalid.utility.functional.iterable.old;

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
class ZipToNonNullablePairNonNullIterable<T1, T2> extends NonNullableIterable<NonNullablePair<T1, T2>> {
    
    /**
     * The first iterable with non-nullable elements.
     */
    private final @Nonnull NonNullableIterable<T1> iterable1;
    
    /**
     * The second iterable with non-nullable elements.
     */
    private final @Nonnull NonNullableIterable<T2> iterable2;
    
    /**
     * Creates a zip-to-non-nullable-pair iterable by combining two non-nullable-elements iterables.
     */
    ZipToNonNullablePairNonNullIterable(@Nonnull NonNullableIterable<T1> iterable1, @Nonnull NonNullableIterable<T2> iterable2) {
        this.iterable1 = iterable1;
        this.iterable2 = iterable2;
    }
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Iterator<NonNullablePair<T1, T2>> iterator() {
        return new ZipToNonNullablePairIterator<>(iterable1.iterator(), iterable2.iterator());
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    /**
     * Since only the {@link net.digitalid.utility.functional.iterable.zip.ZipStrategy#SHORTEST_SEQUENCE SHORTEST_SEQUENCE} 
     * zip strategy can be applied, the size is equal to the shortest source iterable.
     */
    @Override
    public int size() {
        return Math.min(iterable1.size(), iterable2.size());
    }
    
}
