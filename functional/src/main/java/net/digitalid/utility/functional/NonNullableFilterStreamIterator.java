package net.digitalid.utility.functional;

import java.util.Iterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 * Cannot be shared among multiple threads. 
 */
public class NonNullableFilterStreamIterator<E> implements Iterator<E> {
    
    private static class Consumable<E> {
        
        public final E value;
        public boolean consumed = false;
        
        Consumable(E value) {
            this.value = value;
        }
        
        public E consume() {
            consumed = true;
            return value;
        }
        
    }
    
    private final Predicate<E> predicate;
    
    private final Iterator<E> iterator;
    
    private Consumable<E> next;
    
    NonNullableFilterStreamIterator(@Nonnull Predicate<E> predicate, @Nonnull @NonNullableElements Iterator<E> iterator) {
        this.predicate = predicate;
        this.iterator = iterator;
    }
    
    private @Nullable E computeNext() {
        @Nullable E nextElement;
        while (iterator.hasNext()) {
            nextElement = iterator.next();
            if (predicate.apply(nextElement)) {
                return nextElement;
            }
        }
        return null;
    }
    
    @Override
    public boolean hasNext() {
        if (next.consumed) {
            if (iterator.hasNext()) {
                next = new Consumable<>(computeNext());
            }
        }
        return !next.consumed;
    }
    
    @Override
    public E next() {
        if (hasNext()) {
            return next.consume();
        }
        return null;
    }
    
}
