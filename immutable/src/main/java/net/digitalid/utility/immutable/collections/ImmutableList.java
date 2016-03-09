package net.digitalid.utility.immutable.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import net.digitalid.utility.immutable.iterators.ImmutableIterator;
import net.digitalid.utility.immutable.iterators.ImmutableListIterator;

/**
 * This class implements an immutable list of non-nullable elements.
 * <p>
 * <em>Important:</em> This list is only immutable in Java 1.7!
 */
public class ImmutableList<E> extends ArrayList<E> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ImmutableList(Collection<? extends E> collection) {
        super(collection);
        
        if (contains(null)) { throw new NullPointerException("An immutable set may not contain null."); }
    }
    
    /**
     * Returns an immutable list with the elements of the given collection in the same order.
     * The given collection is not captured as its elements are copied to the immutable list.
     * 
     * @throws NullPointerException if any of the elements of the given collection is null.
     */
    public static <E> ImmutableList<E> with(Collection<? extends E> collection) {
        return new ImmutableList<>(collection);
    }
    
    /**
     * Returns an immutable list with the elements of the given array in the same order.
     * The given array is not captured as its elements are copied to the immutable list.
     * 
     * @throws NullPointerException if any of the elements of the given array is null.
     */
    @SafeVarargs
    public static <E> ImmutableList<E> with(E... elements) {
        return new ImmutableList<>(Arrays.asList(elements));
    }
    
    /* -------------------------------------------------- NonNullable Results -------------------------------------------------- */
    
    /**
     * {@inheritDoc}
     *
     * @ensure result != null : "The result may not be null.";
     */
    @Override
    public E get(int index) {
        return super.get(index);
    }
    
    /* -------------------------------------------------- Modified Operations -------------------------------------------------- */
    
    @Override
    public ImmutableIterator<E> iterator() {
        return ImmutableIterator.with(super.iterator());
    }
    
    @Override
    public ImmutableListIterator<E> listIterator() {
        return ImmutableListIterator.with(super.listIterator());
    }
    
    @Override
    public ImmutableListIterator<E> listIterator(int index) {
        return ImmutableListIterator.with(super.listIterator(index));
    }
    
    @Override
    public ImmutableList<E> subList(int fromIndex, int toIndex) {
        // Copies the elements of this immutable list, which leads to some overhead but also prevents memory leaks.
        return ImmutableList.with(super.subList(fromIndex, toIndex));
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
    
    @Override
    public final E set(int index, E element) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final void add(int index, E element) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final E remove(int index) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected final void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final void trimToSize() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final void ensureCapacity(int minCapacity) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }
    
}
