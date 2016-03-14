package net.digitalid.utility.functional.iterable.zip;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import net.digitalid.utility.exceptions.UnexpectedValueException;
import net.digitalid.utility.tuples.pair.NullablePair;
import net.digitalid.utility.tuples.pair.Pair;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * The zip-to-nullable-pair iterator implements an iterator that combines elements from two source iterators by combining the elements of each iterator into a nullable pair on the call to "next()". 
 * If any iterator runs out of elements and the given {@link ZipStrategy zip strategy} is {@link ZipStrategy#SHORTEST_SEQUENCE SHORTEST_SEQUENCE}, the remaining elements of the other iterator are discarded. Thus, the length of this iterator is equal to the length of the shorter source iterator.
 * If the given {@link ZipStrategy zip strategy} is {@link ZipStrategy#LONGEST_SEQUENCE LONGEST_SEQUENCE}, the iterable will continue to produce pairs until the longer iterable is exhausted. In this case, if one of the iterables is shorter, a <i>null</i> value will be added to the pair.
 */
@Mutable
public class ZipToNullablePairIterator<T1, T2> implements Iterator<NullablePair<T1, T2>> {
    
    /**
     * The first iterator, which serves as a source for the first element of the pair.
     */
    private final @Nonnull @NullableElements Iterator<T1> iterator1;
    
    /**
     * The second iterator, which serves as a source for the second element of the pair.
     */
    private final @Nonnull @NullableElements Iterator<T2> iterator2;
    
    /**
     * The chosen zip strategy, which defines what to do when one of the iterables is exhausted.
     */
    private final @Nonnull ZipStrategy strategy;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new zip iterator with the given source iterators.
     */
    public ZipToNullablePairIterator(@Nonnull @NullableElements Iterator<T1> iterator1, @Nonnull @NullableElements Iterator<T2> iterator2, @Nonnull ZipStrategy strategy) {
        this.iterator1 = iterator1;
        this.iterator2 = iterator2;
        this.strategy = strategy;
    }
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Override
    public boolean hasNext() {
        switch (strategy) {
            case SHORTEST_SEQUENCE:
                return iterator1.hasNext() && iterator2.hasNext();
            case LONGEST_SEQUENCE:
                return iterator1.hasNext() || iterator2.hasNext();
            default:
                // TODO: is this the correct exception?
                throw UnexpectedValueException.with("strategy", strategy);
        }
    }
    
    @Override
    public NullablePair<T1, T2> next() {
        if (hasNext()) {
            return Pair.withNullable(ZipIteratorUtility.getNextOrNull(iterator1), ZipIteratorUtility.getNextOrNull(iterator2));
        }
        throw new NoSuchElementException("There are no more elements in this zip iterator. This exception could have been prevented by calling 'hasNext()' before calling 'next()' on this iterator.");
    }
    
}
