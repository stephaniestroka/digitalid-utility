package net.digitalid.utility.functional.iterable.map;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.iterable.map.function.UnaryFunction;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * The map iterator implements an iterator that transforms elements from the source iterator
 * to new elements using the provided function.
 * Cannot be shared among multiple threads. 
 */
@Mutable
public class MapIterator<T, E, A> implements Iterator<E> {
    
    /**
     * The function which is used to transform elements from the original iterable to elements
     * of the new iterable.
     */
    private final @Nonnull UnaryFunction<? super T, E, A> function;
    
    /**
     * The iterator which serves as a source for the elements.
     */
    private final @Nonnull Iterator<T> iterator;
    
    private final @Nullable A additionalInformation;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new map iterator with the given source iterator and the given function.
     */
    public MapIterator(@Nonnull @NonNullableElements Iterator<T> iterator, @Nonnull UnaryFunction<? super T, E, A> function, @Nullable A additionalInformation) {
        this.iterator = iterator;
        this.function = function;
        this.additionalInformation = additionalInformation;
    }
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public E next() {
        if (hasNext()) {
            return function.apply(iterator.next(), additionalInformation);
        }
        throw new NoSuchElementException("There are no more elements in this map iterator. This exception could have been prevented by calling 'hasNext()' before calling 'next()' on this iterator");
    }
    
}
