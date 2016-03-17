package net.digitalid.utility.immutable.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

import net.digitalid.utility.immutable.iterators.ImmutableIterator;

/**
 * This class implements an immutable set of non-nullable elements.
 * <p>
 * <em>Important:</em> This set is only immutable in Java 1.7!
 */
public class ImmutableSet<E> extends LinkedHashSet<E> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */

    protected ImmutableSet(Collection<? extends E> collection) {
        super(collection);
        
        if (contains(null)) { throw new NullPointerException("An immutable set may not contain null."); }
    }
    
    protected ImmutableSet(Iterable<? extends E> iterable) {
        for (E element : iterable) {
            if (element == null) { throw new NullPointerException("An immutable set may not contain null."); }
            super.add(element);
        }
    }
    
    /**
     * Returns an immutable set with the elements of the given collection.
     * The given collection is not captured as its elements are copied to the immutable set.
     * 
     * @throws NullPointerException if any of the elements of the given collection is null.
     */
    public static <E> ImmutableSet<E> with(Collection<? extends E> collection) {
        return new ImmutableSet<>(collection);
    }
    
    /**
     * Returns an immutable set with the elements of the given iterable.
     * The given iterable is not captured as its elements are copied to the immutable set.
     * 
     * @throws NullPointerException if any of the elements of the given iterable is null.
     */
    public static <E> ImmutableSet<E> with(Iterable<? extends E> iterable) {
        return new ImmutableSet<>(iterable);
    }
    
    /**
     * Returns an immutable set with the elements of the given array.
     * The given array is not captured as its elements are copied to the immutable set.
     * 
     * @throws NullPointerException if any of the elements of the given array is null.
     */
    @SafeVarargs
    public static <E> ImmutableSet<E> with(E... elements) {
        return new ImmutableSet<>(Arrays.asList(elements));
    }
    
    /* -------------------------------------------------- Modified Operations -------------------------------------------------- */
    
    @Override
    public ImmutableIterator<E> iterator() {
        return ImmutableIterator.with(super.iterator());
    }
    
    /* -------------------------------------------------- Unsupported Operations -------------------------------------------------- */
    
    @Override
    public final boolean add(E e) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final void clear() {
        throw new UnsupportedOperationException();
    }
    
}
