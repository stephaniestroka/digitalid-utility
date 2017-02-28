package net.digitalid.utility.functional.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.functional.exceptions.IterationExceptionBuilder;
import net.digitalid.utility.functional.failable.FailablePredicate;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a filtering iterator that iterates over the elements of the given iterator that fulfill the given predicate.
 */
@Mutable
public class FilteringIterator<@Specifiable ELEMENT> extends SingleIteratorBasedIterator<ELEMENT, ELEMENT> {
    
    /* -------------------------------------------------- Predicate -------------------------------------------------- */
    
    protected final @Nonnull FailablePredicate<? super ELEMENT, ?> predicate;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected FilteringIterator(@Captured @Nonnull Iterator<? extends ELEMENT> primaryIterator, @Nonnull FailablePredicate<? super ELEMENT, ?> predicate) {
        super(primaryIterator);
        
        this.predicate = predicate;
    }
    
    /**
     * Returns a new filtering iterator that iterates over the elements of the given iterator that fulfill the given predicate.
     */
    @Pure
    public static @Capturable <@Specifiable ELEMENT> @Nonnull FilteringIterator<ELEMENT> with(@Captured @Nonnull Iterator<? extends ELEMENT> iterator, @Nonnull FailablePredicate<? super ELEMENT, ?> predicate) {
        return new FilteringIterator<>(iterator, predicate);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    private @Nullable ELEMENT nextElement = null;
    
    private boolean found = false;
    
    @Pure
    @Override
    public boolean hasNext() {
        if (found) {
            return true;
        } else {
            try {
                while (primaryIterator.hasNext()) {
                    final ELEMENT element = primaryIterator.next();
                    if (predicate.evaluate(element)) {
                        nextElement = element;
                        found = true;
                        return true;
                    }
                }
                return false;
            } catch (@Nonnull Exception exception) {
                throw IterationExceptionBuilder.withCause(exception).build();
            }
        }
    }
    
    @Impure
    @Override
    public @NonCapturable ELEMENT next() {
        if (hasNext()) {
            found = false;
            return nextElement;
        } else {
            throw new NoSuchElementException();
        }
    }
    
}
