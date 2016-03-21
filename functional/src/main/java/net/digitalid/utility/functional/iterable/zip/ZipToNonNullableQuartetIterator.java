package net.digitalid.utility.functional.iterable.zip;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import net.digitalid.utility.tupless.quartet.NonNullableQuartet;
import net.digitalid.utility.tupless.quartet.Quartet;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * The non-nullable zip-to-quartet iterator implements an iterator that combines elements from three source iterators by combining the elements of each iterator into a non-nullable quartet on the call to "next()". If any iterator runs out of elements, the remaining elements of the other iterators are discarded. Thus, the length of this iterator is equal to the length of the shortest source iterator.
 */
@Mutable
public class ZipToNonNullableQuartetIterator<T1, T2, T3, T4> implements Iterator<NonNullableQuartet<T1, T2, T3, T4>> {
    
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
     * The fourth iterator, which serves as a source for the fourth element of the quartet.
     */
    private final @Nonnull @NullableElements Iterator<T4> iterator4;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new zip iterator with the given source iterators.
     */
    public ZipToNonNullableQuartetIterator(@Nonnull @NullableElements Iterator<T1> iterator1, @Nonnull @NullableElements Iterator<T2> iterator2, @Nonnull @NullableElements Iterator<T3> iterator3, @Nonnull @NullableElements Iterator<T4> iterator4) {
        this.iterator1 = iterator1;
        this.iterator2 = iterator2;
        this.iterator3 = iterator3;
        this.iterator4 = iterator4;
    }
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Override
    public boolean hasNext() {
        return iterator1.hasNext() && iterator2.hasNext() && iterator3.hasNext() && iterator4.hasNext();
    }
    
    @Override
    public NonNullableQuartet<T1, T2, T3, T4> next() {
        if (hasNext()) {
            return Quartet.withNonNullable(iterator1.next(), iterator2.next(), iterator3.next(), iterator4.next());
        }
        throw new NoSuchElementException("There are no more elements in this zip iterator. This exception could have been prevented by calling 'hasNext()' before calling 'next()' on this iterator.");
    }
    
}
