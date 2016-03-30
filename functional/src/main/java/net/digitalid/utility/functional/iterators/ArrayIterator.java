package net.digitalid.utility.functional.iterators;

import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.reference.Capturable;
import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.math.NonNegative;

/**
 * This class implements an array iterator that iterates over the given elements.
 */
@Mutable
public class ArrayIterator<E> implements ReadOnlyIterator<E> {
    
    /* -------------------------------------------------- Elements -------------------------------------------------- */
    
    protected final @Nonnull E[] elements;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    @SafeVarargs
    protected ArrayIterator(E... elements) {
        this.elements = elements;
    }
    
    /**
     * Returns a new array iterator that iterates over the given elements.
     */
    @Pure
    @SafeVarargs
    public static <E> @Capturable @Nonnull ArrayIterator<E> with(E... elements) {
        return new ArrayIterator<>(elements);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    private @NonNegative int cursor = 0;
    
    @Pure
    @Override
    public boolean hasNext() {
        return cursor < elements.length;
    }
    
    @Override
    public E next() {
        if (hasNext()) { return elements[cursor++]; }
        else { throw new NoSuchElementException(); }
    }
    
}
