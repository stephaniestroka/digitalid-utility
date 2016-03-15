package net.digitalid.utility.functional.iterable.combine;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * The combine iterator implements an iterator that combines the elements of two or more iterators sequentially.
 * Cannot be shared among multiple threads. 
 */
@Mutable
public class CombineIterator<T> implements Iterator<T> {
    
    /**
     * The list of iterators which serves as a source for the elements.
     */
    private final List<Iterator<T>> iterators;
    
    private int index = 0;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new zip iterator with the given source iterators.
     */
    public CombineIterator(@Nonnull @NonNullableElements List<Iterator<T>> iterators) {
        this.iterators = iterators;
    }
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Override
    public boolean hasNext() {
        while (index < iterators.size()) {
            if (iterators.get(index).hasNext()) {
                return true;
            } else {
                index++;
            }
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
