package net.digitalid.utility.functional.iterable.old;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.zip.ZipStrategy;
import net.digitalid.utility.functional.iterable.zip.ZipToNullableQuartetIterator;
import net.digitalid.utility.tupless.quartet.NullableQuartet;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * The zip-to-nullable-quartet iterable implements an iterable that combines four nullable-elements iterables and returns an iterator that returns quartets of the elements of the source iterables.
 */
@Immutable
class ZipToNullableQuartetNonNullIterable<T1, T2, T3, T4> extends NonNullableIterable<NullableQuartet<T1, T2, T3, T4>> {
    
    /**
     * The first iterable with nullable elements.
     */
    private final @Nonnull NullableIterable<T1> iterable1;
    
    /**
     * The second iterable with nullable elements.
     */
    private final @Nonnull NullableIterable<T2> iterable2;
    
    /**
     * The third iterable with nullable elements.
     */
    private final @Nonnull NullableIterable<T3> iterable3;
    
    /**
     * The forth iterable with nullable elements.
     */
    private final @Nonnull NullableIterable<T4> iterable4;
    
    /**
     * The chosen zip strategy, which defines what to do when one of the iterables is exhausted.
     */
    private final @Nonnull ZipStrategy strategy;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a zip-to-nullable-quartet iterable by combining four nullable-elements iterables.
     * If the length of the given iterables is not equal, the zip iterator will return pairs on a call to <i>next()</i>
     * until the shortest iterator is exhausted.
     */
    ZipToNullableQuartetNonNullIterable(@Nonnull NullableIterable<T1> iterable1, @Nonnull NullableIterable<T2> iterable2, @Nonnull NullableIterable<T3> iterable3, @Nonnull NullableIterable<T4> iterable4) {
        this(iterable1, iterable2, iterable3, iterable4, ZipStrategy.SHORTEST_SEQUENCE);
    }
    
    /**
     * Creates a zip-to-nullable-quartet iterable by combining four nullable-elements iterables.
     *
     * @param strategy the strategy of the zip iterator. Either the iterator stops if the shortest iterable reached its end ({@link ZipStrategy#SHORTEST_SEQUENCE SHORTEST_SEQUENCE}), or it continues to produce pairs by adding <i>null</i> values for fully consumed iterables, until the longest iterable reached its end ({@link ZipStrategy#LONGEST_SEQUENCE LONGEST_SEQUENCE}).
     */
    ZipToNullableQuartetNonNullIterable(@Nonnull NullableIterable<T1> iterable1, @Nonnull NullableIterable<T2> iterable2, @Nonnull NullableIterable<T3> iterable3, @Nonnull NullableIterable<T4> iterable4, @Nonnull ZipStrategy strategy) {
        this.iterable1 = iterable1;
        this.iterable2 = iterable2;
        this.iterable3 = iterable3;
        this.iterable4 = iterable4;
        this.strategy = strategy;
    }
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Iterator<NullableQuartet<T1, T2, T3, T4>> iterator() {
        return new ZipToNullableQuartetIterator<>(iterable1.iterator(), iterable2.iterator(), iterable3.iterator(), iterable4.iterator(), strategy);
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    /**
     * If the {@link net.digitalid.utility.functional.iterable.zip.ZipStrategy#SHORTEST_SEQUENCE SHORTEST_SEQUENCE}
     * zip strategy was applied, the size is equal to the shortest source iterable.
     * Otherwise, if the {@link net.digitalid.utility.functional.iterable.zip.ZipStrategy#LONGEST_SEQUENCE LONGEST_SEQUENCE}
     * zip strategy was applied, the size is equal to the longest source iterable.
     */
    @Override
    public int size() {
        final int sizeIterable1 = iterable1.size() == -1 ? Integer.MAX_VALUE : iterable1.size();
        final int sizeIterable2 = iterable2.size() == -1 ? Integer.MAX_VALUE : iterable2.size();
        final int sizeIterable3 = iterable3.size() == -1 ? Integer.MAX_VALUE : iterable3.size();
        final int sizeIterable4 = iterable4.size() == -1 ? Integer.MAX_VALUE : iterable4.size();
        final int size;
        if (strategy == ZipStrategy.SHORTEST_SEQUENCE) {
            size = Math.min(Math.min(Math.min(sizeIterable1, sizeIterable2), sizeIterable3), sizeIterable4);
        } else {
            size = Math.max(Math.max(Math.max(sizeIterable1, sizeIterable2), sizeIterable3), sizeIterable4);
        }
        return size == Integer.MAX_VALUE ? -1 : size;
    }
    
}
