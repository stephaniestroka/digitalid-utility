package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.zip.ZipStrategy;
import net.digitalid.utility.functional.iterable.zip.ZipToNullablePairIterator;
import net.digitalid.utility.tuples.pair.NullablePair;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * The zip iterable implements an iterable that combines two or more nullable-elements iterables and returns an iterator that alternates the retrieval of the next element.
 */
@Immutable
class ZipToNullablePairNonNullIterable<T1, T2> extends NonNullIterable<NullablePair<T1, T2>> {
    
    /**
     * The first iterable with nullable elements.
     */
    private final @Nonnull NullableIterable<T1> iterable1;
    
    /**
     * The second iterable with nullable elements.
     */
    private final @Nonnull NullableIterable<T2> iterable2;
    
    /**
     * The chosen zip strategy, which defines what to do when one of the iterables is exhausted.
     */
    private final @Nonnull ZipStrategy strategy;
    
    /**
     * Creates a zip-to-nullable-pair iterable by combining two nullable-elements iterables. 
     * If the length of the given iterables is not equal, the zip iterator will return pairs on a call to <i>next()</i>
     * until the shortest iterator is exhausted.
     */
    ZipToNullablePairNonNullIterable(@Nonnull NullableIterable<T1> iterable1, @Nonnull NullableIterable<T2> iterable2) {
        this(iterable1, iterable2, ZipStrategy.SHORTEST_SEQUENCE);
    }
    
    /**
     * Creates a zip-to-nullable-pair iterable by combining two nullable-elements iterables.
     * 
     * @param strategy the strategy of the zip iterator. Either the iterator stops if the shortest iterable reached its end ({@link ZipStrategy#SHORTEST_SEQUENCE SHORTEST_SEQUENCE}), or it continues to produce pairs by adding <i>null</i> values for fully consumed iterables, until the longest iterable reached its end ({@link ZipStrategy#LONGEST_SEQUENCE LONGEST_SEQUENCE}).
     */
    ZipToNullablePairNonNullIterable(@Nonnull NullableIterable<T1> iterable1, @Nonnull NullableIterable<T2> iterable2, @Nonnull ZipStrategy strategy) {
        this.iterable1 = iterable1;
        this.iterable2 = iterable2;
        this.strategy = strategy;
    }
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Iterator<NullablePair<T1, T2>> iterator() {
        return new ZipToNullablePairIterator<>(iterable1.iterator(), iterable2.iterator(), strategy);
    }
    
    @Override
    public int size() {
        if (strategy == ZipStrategy.SHORTEST_SEQUENCE) {
            return Math.min(iterable1.size(), iterable2.size());
        } else {
            return Math.max(iterable1.size(), iterable2.size());
        }
    }
    
}
