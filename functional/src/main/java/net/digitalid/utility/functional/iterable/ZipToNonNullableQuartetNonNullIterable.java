package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.zip.ZipToNonNullableQuartetIterator;
import net.digitalid.utility.tuples.quartet.NonNullableQuartet;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * The zip-to-non-nullable-quartet iterable implements an iterable that combines four non-nullable-elements iterables and returns an iterator that returns quartets of the elements of the source iterables.
 */
@Immutable
class ZipToNonNullableQuartetNonNullIterable<T1, T2, T3, T4> extends NonNullIterable<NonNullableQuartet<T1, T2, T3, T4>> {
    
    /**
     * The first iterable with non-nullable elements.
     */
    private final @Nonnull NonNullIterable<T1> iterable1;
    
    /**
     * The second iterable with non-nullable elements.
     */
    private final @Nonnull NonNullIterable<T2> iterable2;
    
    /**
     * The third iterable with non-nullable elements.
     */
    private final @Nonnull NonNullIterable<T3> iterable3;
    
    /**
     * The forth iterable with non-nullable elements.
     */
    private final @Nonnull NonNullIterable<T4> iterable4;
    
    /**
     * Creates a zip-to-non-nullable-quartet iterable by combining four non-nullable-elements iterables.
     */
    ZipToNonNullableQuartetNonNullIterable(@Nonnull NonNullIterable<T1> iterable1, @Nonnull NonNullIterable<T2> iterable2, @Nonnull NonNullIterable<T3> iterable3, @Nonnull NonNullIterable<T4> iterable4) {
        this.iterable1 = iterable1;
        this.iterable2 = iterable2;
        this.iterable3 = iterable3;
        this.iterable4 = iterable4;
    }
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Iterator<NonNullableQuartet<T1, T2, T3, T4>> iterator() {
        return new ZipToNonNullableQuartetIterator<>(iterable1.iterator(), iterable2.iterator(), iterable3.iterator(), iterable4.iterator());
    }
    
    @Override
    public int size() {
        return Math.min(Math.min(Math.min(iterable1.size(), iterable2.size()), iterable3.size()), iterable4.size());
    }
    
}
