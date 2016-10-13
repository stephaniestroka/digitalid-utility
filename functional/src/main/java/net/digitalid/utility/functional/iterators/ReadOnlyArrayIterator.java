package net.digitalid.utility.functional.iterators;

import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.Shared;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements an array iterator that iterates over the given elements.
 */
@Mutable
public class ReadOnlyArrayIterator<E> extends ReadOnlyIterator<E> {
    
    /* -------------------------------------------------- Elements -------------------------------------------------- */
    
    protected final @Shared @Nonnull E[] elements;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    @SafeVarargs
    protected ReadOnlyArrayIterator(@Shared @Unmodified @Nonnull E... elements) {
        this.elements = elements;
    }
    
    /**
     * Returns a new read-only array iterator that iterates over the given elements.
     */
    @Pure
    @SafeVarargs
    public static @Capturable <E> @Nonnull ReadOnlyArrayIterator<E> with(@Shared @Unmodified @Nonnull E... elements) {
        return new ReadOnlyArrayIterator<>(elements);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    private @NonNegative int cursor = 0;
    
    @Pure
    @Override
    public boolean hasNext() {
        return cursor < elements.length;
    }
    
    @Impure
    @Override
    public @NonCapturable E next() {
        if (hasNext()) { return elements[cursor++]; }
        else { throw new NoSuchElementException(); }
    }
    
    /**
     * Returns the index of the next element.
     * 
     * @throws NoSuchElementException if there is no next element.
     */
    @Pure
    public @NonNegative int nextIndex() {
        if (hasNext()) { return cursor; }
        else { throw new NoSuchElementException(); }
    }
    
    /**
     * Returns whether this iterator has a previous element.
     */
    @Pure
    public boolean hasPrevious() {
        return cursor > 0;
    }
    
    /**
     * Returns the previous element of this iterator.
     */
    @Impure
    public @NonCapturable E previous() {
        if (hasPrevious()) { return elements[--cursor]; }
        else { throw new NoSuchElementException(); }
    }
    
    /**
     * Returns the index of the previous element.
     * 
     * @throws NoSuchElementException if there is no previous element.
     */
    @Pure
    public @NonNegative int previousIndex() {
        if (hasPrevious()) { return cursor - 1; }
        else { throw new NoSuchElementException(); }
    }
    
}
