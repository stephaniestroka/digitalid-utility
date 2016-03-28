package net.digitalid.utility.functional.iterators;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import net.digitalid.utility.functional.iterables.CollectionIterable;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This class implements a flattening iterator that iterates over the elements of the given iterator with all collections up to the given level flattened.
 */
public class FlatteningIterator<F, E> extends SingleIteratorBasedIterator<F, E> {
    
    /* -------------------------------------------------- Level -------------------------------------------------- */
    
    protected final long level;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected FlatteningIterator(Iterator<? extends E> primaryIterator, long level) {
        super(primaryIterator);
        
        this.level = level;
    }
    
    /**
     * Returns a new flattening iterator that iterates over the elements of the given iterator with all collections up to the given level flattened.
     */
    @Pure
    public static <F, E> FlatteningIterator<F, E> with(Iterator<? extends E> iterator, long level) {
        return new FlatteningIterator<>(iterator, level);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    private E nextElement = null;
    
    private boolean found = false;
    
    private Iterator<F> subiterator = null;
    
    @Pure
    @Override
    @SuppressWarnings("unchecked")
    public boolean hasNext() {
        if (subiterator != null) {
            if (subiterator.hasNext()) {
                return true;
            } else {
                subiterator = null;
            }
        }
        
        if (found) {
            return true;
        } else {
            while (primaryIterator.hasNext()) {
                final E element = primaryIterator.next();
                if (level > 0 && element instanceof Collection) {
                    subiterator = CollectionIterable.of((Collection<?>) element).<F>flatten(level - 1).iterator();
                    if (subiterator.hasNext()) {
                        return true;
                    } else {
                        subiterator = null;
                    }
                }
                nextElement = element;
                found = true;
                return true;
            }
            return false;
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public F next() {
        if (hasNext()) {
            if (subiterator != null) {
                return subiterator.next();
            } else {
                found = false;
                return (F) nextElement;
            }
        } else {
            throw new NoSuchElementException();
        }
    }
    
}
