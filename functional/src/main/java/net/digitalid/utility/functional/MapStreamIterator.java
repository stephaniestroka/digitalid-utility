package net.digitalid.utility.functional;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 * Cannot be shared among multiple threads. 
 */
public class MapStreamIterator<T, E> implements Iterator<E> {
    
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
    
    private final @Nonnull Function<T, E> function;
    
    private final Iterator<T> iterator;
    
    private Consumable<E> next;
    
    MapStreamIterator(@Nonnull Function<T, E> function, @Nonnull @NonNullableElements Iterator<T> iterator) {
        this.function = function;
        this.iterator = iterator;
    }
    
    @Override
    public boolean hasNext() {
        if (next.consumed) {
            if (iterator.hasNext()) {
                next = new Consumable<>(function.apply(iterator.next()));
            }
        }
        return !next.consumed;
    }
    
    @Override
    public E next() {
        if (hasNext()) {
            return next.consume();
        }
        // TODO: should we throw an out-of-range exception here?
        return null;
    }
    
}
