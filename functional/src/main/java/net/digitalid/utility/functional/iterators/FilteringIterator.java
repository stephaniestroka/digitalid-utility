package net.digitalid.utility.functional.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.functional.exceptions.FailedIterationException;
import net.digitalid.utility.functional.failable.FailablePredicate;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a filtering iterator that iterates over the elements of the given iterator that fulfill the given predicate.
 */
@Mutable
public class FilteringIterator<E> extends SingleIteratorBasedIterator<E, E> {
    
    /* -------------------------------------------------- Predicate -------------------------------------------------- */
    
    protected final @Nonnull FailablePredicate<? super E, ?> predicate;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected FilteringIterator(@Captured @Nonnull Iterator<? extends E> primaryIterator, @Nonnull FailablePredicate<? super E, ?> predicate) {
        super(primaryIterator);
        
        this.predicate = predicate;
    }
    
    /**
     * Returns a new filtering iterator that iterates over the elements of the given iterator that fulfill the given predicate.
     */
    @Pure
    public static @Capturable <E> @Nonnull FilteringIterator<E> with(@Captured @Nonnull Iterator<? extends E> iterator, @Nonnull FailablePredicate<? super E, ?> predicate) {
        return new FilteringIterator<>(iterator, predicate);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    private @Nullable E nextElement = null;
    
    private boolean found = false;
    
    @Pure
    @Override
    public boolean hasNext() {
        if (found) {
            return true;
        } else {
            try {
                while (primaryIterator.hasNext()) {
                    final E element = primaryIterator.next();
                    if (predicate.evaluate(element)) {
                        nextElement = element;
                        found = true;
                        return true;
                    }
                }
                return false;
            } catch (@Nonnull Exception exception) {
                throw FailedIterationException.with(exception);
            }
        }
    }
    
    @Impure
    @Override
    public @NonCapturable E next() {
        if (hasNext()) {
            found = false;
            return nextElement;
        } else {
            throw new NoSuchElementException();
        }
    }
    
}
