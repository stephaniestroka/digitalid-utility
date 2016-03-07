package net.digitalid.utility.functional.iterable.zip;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * The zip iterator implements an iterator that combines elements from the source iterators by alternating the iterator on every call to "next()".
 */
@Mutable
public class ZipIterator<T> implements Iterator<T> {
    
    /**
     * The iterators which serves as a source for the elements.
     */
    private final List<Iterator<T>> iterators;
    
    /**
     * The index that indicates from which iterator the next element can be retrieved.
     */
    private int index = 0;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new zip iterator with the given left and right source iterators.
     */
    public ZipIterator(@Nonnull @NonNullableElements List<Iterator<T>> iterators) {
        this.iterators = iterators;
    }
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Override
    public boolean hasNext() {
        for (int i = 0; i < iterators.size(); i++) {
            if (iterators.get(index).hasNext()) {
                return true;
            }
            index = (index + 1) % iterators.size();
        }
        return false;
    }
    
    @Override
    public T next() {
        if (hasNext()) {
            return iterators.get(index).next();
        }
        throw new NoSuchElementException("There are no more elements in this map iterator. This exception could have been prevented by calling 'hasNext()' before calling 'next()' on this iterator");
    }
    
}
