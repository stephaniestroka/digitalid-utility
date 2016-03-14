package net.digitalid.utility.functional.iterable.array;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 *
 */
@Mutable
public class ArrayIterator<T> implements Iterator<T> {
    
    private final @Nullable T[] array;
    
    private int index;
    
    public ArrayIterator(@Nullable T[] array) {
        this.array = array;
    }
    
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
            throw new NoSuchElementException("There are no more elements in this map iterator. This exception could have been prevented by calling 'hasNext()' before calling 'next()' on this iterator");
        }
    }
    
}
