package net.digitalid.utility.functional.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.annotations.method.Pure;

/**
 * This class implements a filtering iterator that iterates over the elements of the given iterator that fulfill the given predicate.
 */
public class FilteringIterator<E> extends SingleIteratorBasedIterator<E, E> {
    
    /* -------------------------------------------------- Predicate -------------------------------------------------- */
    
    protected final Predicate<? super E> predicate;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected FilteringIterator(Iterator<? extends E> primaryIterator, Predicate<? super E> predicate) {
        super(primaryIterator);
        
        this.predicate = predicate;
    }
    
    /**
     * Returns a new filtering iterator that iterates over the elements of the given iterator that fulfill the given predicate.
     */
    @Pure
    public static <E> FilteringIterator<E> with(Iterator<? extends E> iterator, Predicate<? super E> predicate) {
        return new FilteringIterator<>(iterator, predicate);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    private E nextElement = null;
    
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
    
    @Override
    public E next() {
        if (hasNext()) {
            found = false;
            return nextElement;
        } else {
            throw new NoSuchElementException();
        }
    }
    
}
