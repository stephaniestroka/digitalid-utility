package net.digitalid.utility.tuples;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.interfaces.CustomComparable;
import net.digitalid.utility.validation.annotations.index.Index;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class makes the tuples iterable.
 * 
 * @see Pair
 */
@Immutable
public abstract class Tuple implements Collection<Object>, CustomComparable<Tuple> {
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    /**
     * Returns the size of this tuple.
     */
    @Pure
    @Override
    public abstract @NonNegative int size();
    
    /* -------------------------------------------------- Access -------------------------------------------------- */
    
    /**
     * Returns the element at the given index.
     * 
     * @throws IndexOutOfBoundsException if the index is negative or greater or equal to the size of this tuple.
     */
    @Pure
    public abstract @NonCapturable Object get(@Index int index);
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Mutable
    public class TupleIterator implements Iterator<Object> {
        
        private int cursor = 0;
        
        @Pure
        @Override
        public boolean hasNext() {
            return cursor < size();
        }
        
        @Impure
        @Override
        public @NonCapturable Object next() {
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
    public @Capturable @Nonnull TupleIterator iterator() {
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
    public boolean contains(@NonCaptured @Unmodified @Nullable Object object) {
        for (@Nullable Object element : this) {
            if (Objects.equals(object, element)) { return true; }
        }
        return false;
    }
    
    @Pure
    @Override
    public boolean containsAll(@NonCaptured @Unmodified @Nonnull Collection<?> collection) {
        for (@Nullable Object element : collection) {
            if (!contains(element)) { return false; }
        }
        return true;
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull Object[] toArray() {
        final int size = size();
        final @Nonnull Object[] array = new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = get(i);
        }
        return array;
    }
    
    @Pure
    @Override
    @SuppressWarnings("unchecked")
    public @Capturable <T> @Nonnull T[] toArray(@NonCaptured @Modified T[] array) {
        final int size = size();
        final @Nonnull T[] result = array.length >= size ? array : (T[]) Array.newInstance(array.getClass().getComponentType(), size);
        for (int i = 0; i < size(); i++) {
            result[i] = (T) get(i);
        }
        return result;
    }
    
    /* -------------------------------------------------- Unsupported Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean add(@NonCaptured @Unmodified Object object) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public boolean remove(@NonCaptured @Unmodified Object object) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public boolean addAll(@NonCaptured @Unmodified Collection<?> collection) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public boolean removeAll(@NonCaptured @Unmodified Collection<?> collection) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public boolean retainAll(@NonCaptured @Unmodified Collection<?> collection) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    /* -------------------------------------------------- Comparable -------------------------------------------------- */
    
    @Pure
    @Override
    @SuppressWarnings("unchecked")
    public int compareTo(@Nonnull Tuple tuple) {
        final int size = Math.min(size(), tuple.size());
        for (int i = 0; i < size; i++) {
            // According to the Javadoc, we're allowed to throw a ClassCastException.
            final int comparison = ((Comparable<Object>) get(i)).compareTo(tuple.get(i));
            if (comparison != 0) { return comparison; }
        }
        return size() - tuple.size();
    }
    
}
