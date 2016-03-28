package net.digitalid.utility.tuples;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This class makes the tuples iterable.
 * 
 * @see Pair
 */
public abstract class Tuple implements Iterable<Object> {
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    /**
     * Returns the size of this tuple.
     */
    @Pure
    public abstract int size();
    
    /* -------------------------------------------------- Access -------------------------------------------------- */
    
    /**
     * Returns the element at the given index.
     * 
     * @throws IndexOutOfBoundsException if the index is negative or greater or equal to the size of this tuple.
     */
    @Pure
    public abstract Object get(int index);
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    private class TupleIterator implements Iterator<Object> {
        
        private int cursor = 0;
        
        @Pure
        @Override
        public boolean hasNext() {
            return cursor < size();
        }
        
        @Pure
        @Override
        public Object next() {
            if (hasNext()) { return get(cursor++); }
            else { throw new NoSuchElementException(); }
        }
        
        @Pure
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }
    
    @Pure
    @Override
    public Iterator<Object> iterator() {
        return new TupleIterator();
    }
    
}
