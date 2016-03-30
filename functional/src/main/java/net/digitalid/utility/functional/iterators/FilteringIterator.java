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
import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.functional.interfaces.Predicate;

/**
 * This class implements a filtering iterator that iterates over the elements of the given iterator that fulfill the given predicate.
 */
@Mutable
public class FilteringIterator<E> extends SingleIteratorBasedIterator<E, E> {
    
    /* -------------------------------------------------- Predicate -------------------------------------------------- */
    
    protected final @Nonnull Predicate<? super E> predicate;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected FilteringIterator(@Captured @Nonnull Iterator<? extends E> primaryIterator, @Nonnull Predicate<? super E> predicate) {
        super(primaryIterator);
        
        this.predicate = predicate;
    }
    
    /**
     * Returns a new filtering iterator that iterates over the elements of the given iterator that fulfill the given predicate.
     */
    @Pure
    public static <E> @Capturable @Nonnull FilteringIterator<E> with(@Captured @Nonnull Iterator<? extends E> iterator, @Nonnull Predicate<? super E> predicate) {
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
            while (primaryIterator.hasNext()) {
                final E element = primaryIterator.next();
                if (predicate.evaluate(element)) {
                    nextElement = element;
                    found = true;
                    return true;
                }
            }
            return false;
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
