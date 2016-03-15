package net.digitalid.utility.functional.iterable.filter;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.predicate.NonNullPredicate;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * The filter iterator implements an iterator that returns elements of an iterable that satisfy a given predicate.
 */
@Mutable
public class FilterIterator<E> implements Iterator<E> {
    
    /**
     * The predicate which is used to filter elements from an iterator. Only the elements that
     * satisfy the predicate are returned by this iterator.
     */
    private final @Nonnull NonNullPredicate<E> predicate;
    
    /**
     * The iterator which serves as a source for the elements.
     */
    private final @Nonnull @NullableElements Iterator<E> iterator;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new filter iterator with a given source iterator and a given predicate.
     */
    public FilterIterator(@Nonnull @NullableElements Iterator<E> iterator, @Nonnull NonNullPredicate<E> predicate) {
        this.predicate = predicate;
        this.iterator = iterator;
    }
    
    /* -------------------------------------------------- Find Next -------------------------------------------------- */
    
    /**
     * Finds the next element, wraps it into the {@link Consumable} type and returns it.
     * If no further element, that satisfies the predicate, could be found, null is returned.
     */
    @Pure
    private @Nullable Consumable<E> findNext() {
        @Nullable E nextElement;
        while (iterator.hasNext()) {
            nextElement = iterator.next();
            if (predicate.apply(nextElement)) {
                return new Consumable<>(nextElement);
            }
        }
        return null;
    }
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    /**
     * Wraps the next element in a consumable type.
     */
    private @Nonnull Consumable<E> next = new Consumable.InitialConsumable<>();
    
    @Override
    public boolean hasNext() {
        if (next.isConsumed()) {
            if (iterator.hasNext()) {
                final @Nullable Consumable<E> nextElement = findNext();
                if (nextElement != null) {
                    next = nextElement;
                }
            }
        }
        return !next.isConsumed();
    }
    
    @Override
    public E next() {
        if (hasNext()) {
            return next.consume();
        }
        throw new NoSuchElementException("There are no more elements in this filter iterator. This exception could have been prevented by calling 'hasNext()' before calling 'next()' on this iterator.");
    }
    
}
