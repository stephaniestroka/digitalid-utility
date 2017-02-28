package net.digitalid.utility.functional.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a pruning iterator that iterates over the elements of the given iterator from the given start index to but not including the given end index.
 * If the end index is {@link Integer#MAX_VALUE}, this iterator returns values as long as the given iterator does, which is important for skipping infinite iterables.
 */
@Mutable
public class PruningIterator<@Specifiable ELEMENT> extends SingleIteratorBasedIterator<ELEMENT, ELEMENT> {
    
    /* -------------------------------------------------- Indexes -------------------------------------------------- */
    
    protected final @NonNegative int startIndex;
    
    protected final @Positive int endIndex;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected PruningIterator(@Captured @Nonnull Iterator<? extends ELEMENT> primaryIterator, @NonNegative int startIndex, @Positive int endIndex) {
        super(primaryIterator);
        
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }
    
    /**
     * Returns a new pruning iterator that iterates over the elements of the given iterator from the given start index to but not including the given end index.
     */
    @Pure
    public static @Capturable <@Specifiable ELEMENT> @Nonnull PruningIterator<ELEMENT> with(@Captured @Nonnull Iterator<? extends ELEMENT> iterator, @NonNegative int startIndex, @Positive int endIndex) {
        return new PruningIterator<>(iterator, startIndex, endIndex);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    private @NonNegative int currentIndex = 0;
    
    @Pure
    @Override
    public boolean hasNext() {
        while (currentIndex < startIndex && primaryIterator.hasNext()) {
            primaryIterator.next();
            currentIndex += 1;
        }
        return (endIndex == Integer.MAX_VALUE || currentIndex < endIndex) && primaryIterator.hasNext();
    }
    
    @Impure
    @Override
    public @NonCapturable ELEMENT next() {
        if (hasNext()) {
            // This condition prevents an index overflow for infinite iterables:
            if (endIndex != Integer.MAX_VALUE) {
                currentIndex += 1;
            }
            return primaryIterator.next();
        } else {
            throw new NoSuchElementException();
        }
    }
    
}
