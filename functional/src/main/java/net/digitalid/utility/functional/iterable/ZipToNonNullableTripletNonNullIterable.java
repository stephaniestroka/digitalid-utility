package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.zip.ZipToNonNullableTripletIterator;
import net.digitalid.utility.tuples.triplet.NonNullableTriplet;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * The zip-to-non-nullable-triplet iterable implements an iterable that combines three non-nullable elements iterables and returns an iterator that returns triplets of the elements of the source iterables.
 */
@Immutable
class ZipToNonNullableTripletNonNullIterable<T1, T2, T3> extends NonNullableIterable<NonNullableTriplet<T1, T2, T3>> {
    
    /**
     * The first iterable with non-nullable elements.
     */
    private final @Nonnull NonNullableIterable<T1> iterable1;
    
    /**
     * The second iterable with non-nullable elements.
     */
    private final @Nonnull NonNullableIterable<T2> iterable2;
    
    /**
     * The third iterable with non-nullable elements.
     */
    private final @Nonnull NonNullableIterable<T3> iterable3;
    
    /**
     * Creates a zip-to-non-nullable-triplet iterable by combining three non-nullable-elements iterables.
     */
    ZipToNonNullableTripletNonNullIterable(@Nonnull NonNullableIterable<T1> iterable1, @Nonnull NonNullableIterable<T2> iterable2, @Nonnull NonNullableIterable<T3> iterable3) {
        this.iterable1 = iterable1;
        this.iterable2 = iterable2;
        this.iterable3 = iterable3;
    }
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Iterator<NonNullableTriplet<T1, T2, T3>> iterator() {
        return new ZipToNonNullableTripletIterator<>(iterable1.iterator(), iterable2.iterator(), iterable3.iterator());
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    /**
     * Since only the {@link net.digitalid.utility.functional.iterable.zip.ZipStrategy#SHORTEST_SEQUENCE SHORTEST_SEQUENCE}
     * zip strategy can be applied, the size is equal to the shortest source iterable.
     */
    @Override
    public int size() {
        final int sizeIterable1 = iterable1.size() == -1 ? Integer.MAX_VALUE : iterable1.size();
        final int sizeIterable2 = iterable2.size() == -1 ? Integer.MAX_VALUE : iterable2.size();
        final int sizeIterable3 = iterable3.size() == -1 ? Integer.MAX_VALUE : iterable3.size();
        final int size = Math.min(Math.min(sizeIterable1, sizeIterable2), sizeIterable3);
        return size == Integer.MAX_VALUE ? -1 : size;
    }
    
}
