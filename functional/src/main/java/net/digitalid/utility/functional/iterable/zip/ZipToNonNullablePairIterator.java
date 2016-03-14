package net.digitalid.utility.functional.iterable.zip;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import net.digitalid.utility.tuples.NonNullablePair;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * The non-nullable zip-to-pair iterator implements an iterator that combines elements from two source iterators by combining the elements of each iterator into a non-nullable pair on the call to "next()". If any iterator runs out of elements, the remaining elements of the other iterator are discarded. Thus, the length of this iterator is equal to the length of the shorter source iterator.
 */
@Mutable
public class ZipToNonNullablePairIterator<T1, T2> implements Iterator<NonNullablePair<T1, T2>> {
    
    /**
     * The first iterator, which serves as a source for the first element of the pair.
     */
    private final @Nonnull @NullableElements Iterator<T1> iterator1;
    
    /**
     * The second iterator, which serves as a source for the second element of the pair.
     */
    private final @Nonnull @NullableElements Iterator<T2> iterator2;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new zip iterator with the given source iterators.
     */
    public ZipToNonNullablePairIterator(@Nonnull @NullableElements Iterator<T1> iterator1, @Nonnull @NullableElements Iterator<T2> iterator2) {
        this.iterator1 = iterator1;
        this.iterator2 = iterator2;
    }
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Override
    public boolean hasNext() {
        return iterator1.hasNext() && iterator2.hasNext();
    }
    
    @Override
    public NonNullablePair<T1, T2> next() {
        if (hasNext()) {
            return NonNullablePair.with(iterator1.next(), iterator2.next());
        }
        throw new NoSuchElementException("There are no more elements in this zip iterator. This exception could have been prevented by calling 'hasNext()' before calling 'next()' on this iterator.");
    }
    
}
