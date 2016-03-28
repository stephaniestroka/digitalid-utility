package net.digitalid.utility.functional.iterable.array;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * Implements the iterator of an array.
 */
@Mutable
public class ArrayIterator<T> implements Iterator<T> {
    
    /**
     * The original array.
     */
    private final @Nullable T[] array;
    
    /**
     * Creates a new array iterator for a given array.
     */
    public ArrayIterator(@Nullable T[] array) {
        this.array = array;
    }
    
    /**
     * The current index into the array
     */
    private int index = 0;
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Override
    public boolean hasNext() {
        return index < array.length;
    }
    
    @Override
    public T next() {
        if (hasNext()) {
            final @Nullable T next = array[index];
            index++;
            return next;
        } else {
            throw new NoSuchElementException("There are no more elements in this array iterator. This exception could have been prevented by calling 'hasNext()' before calling 'next()' on this iterator");
        }
    }
    
    /* -------------------------------------------------- Remove -------------------------------------------------- */
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() is not implemented.");
    }
    
}
