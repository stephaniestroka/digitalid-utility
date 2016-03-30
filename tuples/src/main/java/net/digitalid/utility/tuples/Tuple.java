package net.digitalid.utility.tuples;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.reference.Capturable;
import net.digitalid.utility.annotations.state.Unmodifiable;

/**
 * This class makes the tuples iterable.
 * 
 * @see Pair
 */
@Unmodifiable
public abstract class Tuple implements Collection<Object> {
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    /**
     * Returns the size of this tuple.
     */
    @Pure
    @Override
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
        
        @Override
        public Object next() {
            if (hasNext()) { return get(cursor++); }
            else { throw new NoSuchElementException(); }
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }
    
    @Pure
    @Override
    public @Capturable Iterator<Object> iterator() {
        return new TupleIterator();
    }
    
    /* -------------------------------------------------- Collection -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean isEmpty() {
        return false;
    }
    
    @Pure
    @Override
    public boolean contains(@Nullable Object object) {
        for (Object element : this) {
            if (Objects.equals(object, element)) { return true; }
        }
        return false;
    }
    
    @Pure
    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object element : collection) {
            if (!contains(element)) { return false; }
        }
        return true;
    }
    
    @Pure
    @Override
    public @Capturable Object[] toArray() {
        final int size = size();
        final Object[] array = new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = get(i);
        }
        return array;
    }
    
    @Pure
    @Override
    @SuppressWarnings("unchecked")
    public <T> @Capturable T[] toArray(T[] array) {
        final int size = size();
        final T[] result = array.length >= size ? array : (T[]) Array.newInstance(array.getClass().getComponentType(), size);
        for (int i = 0; i < size(); i++) {
            result[i] = (T) get(i);
        }
        return result;
    }
    
    /* -------------------------------------------------- Unsupported Operations -------------------------------------------------- */
    
    @Override
    public boolean add(Object object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean remove(Object object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean addAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
}
