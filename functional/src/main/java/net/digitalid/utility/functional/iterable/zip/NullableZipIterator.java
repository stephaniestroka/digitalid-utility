package net.digitalid.utility.functional.iterable.zip;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import net.digitalid.utility.tuples.NullablePair;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * The zip iterator implements an iterator that combines elements from the source iterators by alternating the iterator on every call to "next()".
 */
@Mutable
public class NullableZipIterator<T1, T2> implements Iterator<NullablePair<T1, T2>> {
    
    /**
     * The iterators which serves as a source for the elements.
     */
    private final @Nonnull @NullableElements Iterator<T1> iterator1;
    
    private final @Nonnull @NullableElements Iterator<T2> iterator2;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new zip iterator with the given left and right source iterators.
     */
    public NullableZipIterator(@Nonnull @NullableElements Iterator<T1> iterator1, @Nonnull @NullableElements Iterator<T2> iterator2) {
        this.iterator1 = iterator1;
        this.iterator2 = iterator2;
    }
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Override
    public boolean hasNext() {
        return iterator1.hasNext() && iterator2.hasNext();
    }
    
    @Override
    public NullablePair<T1, T2> next() {
        if (hasNext()) {
            return NullablePair.with(iterator1.next(), iterator2.next());
        }
        throw new NoSuchElementException("There are no more elements in this map iterator. This exception could have been prevented by calling 'hasNext()' before calling 'next()' on this iterator.");
    }
    
}
