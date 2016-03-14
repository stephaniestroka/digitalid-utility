package net.digitalid.utility.functional.iterable.zip;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import net.digitalid.utility.tuples.triplet.NonNullableTriplet;
import net.digitalid.utility.tuples.triplet.Triplet;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * The non-nullable zip-to-triplet iterator implements an iterator that combines elements from three source iterators by combining the elements of each iterator into a non-nullable triplet on the call to "next()". If any iterator runs out of elements, the remaining elements of the other iterators are discarded. Thus, the length of this iterator is equal to the length of the shortest source iterator.
 */
@Mutable
public class ZipToNonNullableTripletIterator<T1, T2, T3> implements Iterator<NonNullableTriplet<T1, T2, T3>> {
    
    /**
     * The first iterator, which serves as a source for the first element of the triplet.
     */
    private final @Nonnull @NullableElements Iterator<T1> iterator1;
    
    /**
     * The second iterator, which serves as a source for the second element of the triplet.
     */
    private final @Nonnull @NullableElements Iterator<T2> iterator2;
    
    /**
     * The third iterator, which serves as a source for the third element of the triplet.
     */
    private final @Nonnull @NullableElements Iterator<T3> iterator3;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new zip iterator with the given source iterators.
     */
    public ZipToNonNullableTripletIterator(@Nonnull @NullableElements Iterator<T1> iterator1, @Nonnull @NullableElements Iterator<T2> iterator2, @Nonnull @NullableElements Iterator<T3> iterator3) {
        this.iterator1 = iterator1;
        this.iterator2 = iterator2;
        this.iterator3 = iterator3;
    }
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Override
    public boolean hasNext() {
        return iterator1.hasNext() && iterator2.hasNext() && iterator3.hasNext();
    }
    
    @Override
    public NonNullableTriplet<T1, T2, T3> next() {
        if (hasNext()) {
            return Triplet.withNonNullable(iterator1.next(), iterator2.next(), iterator3.next());
        }
        throw new NoSuchElementException("There are no more elements in this zip iterator. This exception could have been prevented by calling 'hasNext()' before calling 'next()' on this iterator.");
    }
    
}
