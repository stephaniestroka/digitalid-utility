package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.zip.ZipToNonNullableTripletIterator;
import net.digitalid.utility.tuples.NonNullableTriplet;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * The zip-to-non-nullable-triplet iterable implements an iterable that combines three non-nullable elements iterables and returns an iterator that returns triplets of the elements of the source iterables.
 */
@Immutable
class ZipToNonNullableTripletNonNullIterable<T1, T2, T3> extends NonNullIterable<NonNullableTriplet<T1, T2, T3>> {
    
    /**
     * The first iterable with non-nullable elements.
     */
    private final @Nonnull @NonNullableElements Iterable<T1> iterable1;
    
    /**
     * The second iterable with non-nullable elements.
     */
    private final @Nonnull @NonNullableElements Iterable<T2> iterable2;
    
    /**
     * The third iterable with non-nullable elements.
     */
    private final @Nonnull @NonNullableElements Iterable<T3> iterable3;
    
    /**
     * Creates a zip-to-non-nullable-triplet iterable by combining three non-nullable-elements iterables.
     */
    ZipToNonNullableTripletNonNullIterable(@Nonnull @NonNullableElements Iterable<T1> iterable1, @Nonnull @NonNullableElements Iterable<T2> iterable2, @Nonnull @NonNullableElements Iterable<T3> iterable3) {
        this.iterable1 = iterable1;
        this.iterable2 = iterable2;
        this.iterable3 = iterable3;
    }
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Iterator<NonNullableTriplet<T1, T2, T3>> iterator() {
        return new ZipToNonNullableTripletIterator<>(iterable1.iterator(), iterable2.iterator(), iterable3.iterator());
    }
    
}
