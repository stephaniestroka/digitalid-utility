package net.digitalid.utility.functional.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.math.Positive;

/**
 * This class implements a pruning iterator that iterates over the elements of the given iterator from the given start index to but not including the given end index.
 */
@Mutable
public class PruningIterator<E> extends SingleIteratorBasedIterator<E, E> {
    
    /* -------------------------------------------------- Indexes -------------------------------------------------- */
    
    protected final @NonNegative long startIndex;
    
    protected final @Positive long endIndex;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected PruningIterator(@Nonnull Iterator<? extends E> primaryIterator, @NonNegative long startIndex, @Positive long endIndex) {
        super(primaryIterator);
        
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }
    
    /**
     * Returns a new pruning iterator that iterates over the elements of the given iterator from the given start index to but not including the given end index.
     */
    @Pure
    public static <E> @Capturable @Nonnull PruningIterator<E> with(@Nonnull Iterator<? extends E> iterator, @NonNegative long startIndex, @Positive long endIndex) {
        return new PruningIterator<>(iterator, startIndex, endIndex);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    private @NonNegative long currentIndex = 0;
    
    @Pure
    @Override
    public boolean hasNext() {
        while (currentIndex < startIndex && primaryIterator.hasNext()) {
            primaryIterator.next();
            currentIndex += 1;
        }
        return currentIndex < endIndex && primaryIterator.hasNext();
    }
    
    @Override
    public E next() {
        if (hasNext()) {
            currentIndex += 1;
            return primaryIterator.next();
        } else {
            throw new NoSuchElementException();
        }
    }
    
}
