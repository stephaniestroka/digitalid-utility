package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.zip.ZipStrategy;
import net.digitalid.utility.functional.iterable.zip.ZipToNullableTripletIterator;
import net.digitalid.utility.tuples.triplet.NullableTriplet;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * The zip-to-nullable-triplet iterable implements an iterable that combines three nullable elements iterables and returns an iterator that returns triplets of the elements of the source iterables.
 */
@Immutable
class ZipToNullableTripletNonNullIterable<T1, T2, T3> extends NonNullIterable<NullableTriplet<T1, T2, T3>> {
    
    /**
     * The first iterable with nullable elements.
     */
    private final @Nonnull @NullableElements Iterable<T1> iterable1;
    
    /**
     * The second iterable with nullable elements.
     */
    private final @Nonnull @NullableElements Iterable<T2> iterable2;
    
    /**
     * The third iterable with nullable elements.
     */
    private final @Nonnull @NullableElements Iterable<T3> iterable3;
    
    /**
     * The chosen zip strategy, which defines what to do when one of the iterables is exhausted.
     */
    private final @Nonnull ZipStrategy strategy;
    
    /**
     * Creates a zip-to-nullable-triplet iterable by combining three nullable-elements iterables.
     * If the length of the given iterables is not equal, the zip iterator will return pairs on a call to <i>next()</i>
     * until the shortest iterator is exhausted.
     */
    ZipToNullableTripletNonNullIterable(@Nonnull @NullableElements Iterable<T1> iterable1, @Nonnull @NullableElements Iterable<T2> iterable2, @Nonnull @NullableElements Iterable<T3> iterable3) {
        this(iterable1, iterable2, iterable3, ZipStrategy.SHORTEST_SEQUENCE);
    }
    
    /**
     * Creates a zip-to-nullable-triplet iterable by combining three nullable-elements iterables.
     *
     * @param strategy the strategy of the zip iterator. Either the iterator stops if the shortest iterable reached its end ({@link ZipStrategy#SHORTEST_SEQUENCE SHORTEST_SEQUENCE}), or it continues to produce pairs by adding <i>null</i> values for fully consumed iterables, until the longest iterable reached its end ({@link ZipStrategy#LONGEST_SEQUENCE LONGEST_SEQUENCE}).
     */
    ZipToNullableTripletNonNullIterable(@Nonnull @NullableElements Iterable<T1> iterable1, @Nonnull @NullableElements Iterable<T2> iterable2, @Nonnull @NullableElements Iterable<T3> iterable3, @Nonnull ZipStrategy strategy) {
        this.iterable1 = iterable1;
        this.iterable2 = iterable2;
        this.iterable3 = iterable3;
        this.strategy = strategy;
    }
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Iterator<NullableTriplet<T1, T2, T3>> iterator() {
        return new ZipToNullableTripletIterator<>(iterable1.iterator(), iterable2.iterator(), iterable3.iterator(), strategy);
    }
    
}
