package net.digitalid.utility.functional.iterable.zip;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import net.digitalid.utility.exceptions.UnexpectedValueException;
import net.digitalid.utility.tupless.quartet.NullableQuartet;
import net.digitalid.utility.tupless.quartet.Quartet;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * The nullable zip-to-quartet iterator implements an iterator that combines elements from three source iterators by combining the elements of each iterator into a nullable quartet on the call to "next()". If any iterator runs out of elements, the remaining elements of the other iterators are discarded. Thus, the length of this iterator is equal to the length of the shortest source iterator.
 */
@Mutable
public class ZipToNullableQuartetIterator<T1, T2, T3, T4> implements Iterator<NullableQuartet<T1, T2, T3, T4>> {
    
    /**
     * The first iterator, which serves as a source for the first element of the quartet.
     */
    private final @Nonnull @NullableElements Iterator<T1> iterator1;
    
    /**
     * The second iterator, which serves as a source for the second element of the quartet.
     */
    private final @Nonnull @NullableElements Iterator<T2> iterator2;
    
    /**
     * The third iterator, which serves as a source for the third element of the quartet.
     */
    private final @Nonnull @NullableElements Iterator<T3> iterator3;
    
    /**
     * The fourth iterator, which serves as a source for the forth element of the quartet.
     */
    private final @Nonnull @NullableElements Iterator<T4> iterator4;
    
    /**
     * The chosen zip strategy, which defines what to do when one of the iterables is exhausted.
     */
    private final @Nonnull ZipStrategy strategy;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new zip iterator with the given source iterators.
     */
    public ZipToNullableQuartetIterator(@Nonnull @NullableElements Iterator<T1> iterator1, @Nonnull @NullableElements Iterator<T2> iterator2, @Nonnull @NullableElements Iterator<T3> iterator3, @Nonnull @NullableElements Iterator<T4> iterator4, @Nonnull ZipStrategy strategy) {
        this.iterator1 = iterator1;
        this.iterator2 = iterator2;
        this.iterator3 = iterator3;
        this.iterator4 = iterator4;
        this.strategy = strategy;
    }
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Override
    public boolean hasNext() {
        switch (strategy) {
            case SHORTEST_SEQUENCE:
                return iterator1.hasNext() && iterator2.hasNext() && iterator3.hasNext() && iterator4.hasNext();
            case LONGEST_SEQUENCE:
                return iterator1.hasNext() || iterator2.hasNext() || iterator3.hasNext() || iterator4.hasNext();
            default:
                // TODO: is this the correct exception?
                throw UnexpectedValueException.with("strategy", strategy);
        }
    }
    
    @Override
    public NullableQuartet<T1, T2, T3, T4> next() {
        if (hasNext()) {
            return Quartet.withNullable(ZipIteratorUtility.getNextOrNull(iterator1), ZipIteratorUtility.getNextOrNull(iterator2), ZipIteratorUtility.getNextOrNull(iterator3), ZipIteratorUtility.getNextOrNull(iterator4));
        }
        throw new NoSuchElementException("There are no more elements in this zip iterator. This exception could have been prevented by calling 'hasNext()' before calling 'next()' on this iterator.");
    }
    
}
