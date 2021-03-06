/*
 * Copyright (C) 2017 Synacts GmbH, Switzerland (info@synacts.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.digitalid.utility.tuples;

import java.lang.reflect.Array;
import java.util.Collection;
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
import net.digitalid.utility.interfaces.CustomComparable;
import net.digitalid.utility.validation.annotations.index.Index;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.Mutable;

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
    public class Iterator implements java.util.Iterator<Object> {
        
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
    public @Capturable @Nonnull Iterator iterator() {
        return new Iterator();
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
            if (get(i) == null && tuple.get(i) == null) { continue; }
            if (get(i) == null && tuple.get(i) != null) { return -1; }
            if (get(i) != null && tuple.get(i) == null) { return 1; }
            
            // According to the Javadoc, we're allowed to throw a ClassCastException.
            final int comparison = ((Comparable<Object>) get(i)).compareTo(tuple.get(i));
            if (comparison != 0) { return comparison; }
        }
        return size() - tuple.size();
    }
    
    /* -------------------------------------------------- Tuple Type -------------------------------------------------- */
    
    @Pure
    public static @Nullable Class<? extends Tuple> getTupleType(int size) {
        switch (size) {
            case 2:
                return Pair.class;
            case 3:
                return Triplet.class;
            case 4:
                return Quartet.class;
            case 5:
                return Quintet.class;
            case 6:
                return Sextet.class;
            case 7:
                return Septet.class;
            case 8:
                return Octet.class;
            default:
                return null;
        }
    }
    
}
