package net.digitalid.utility.immutable.iterators;

import java.util.ListIterator;
import java.util.Objects;

/**
 * This class implements an immutable list iterator.
 */
public class ImmutableListIterator<E> extends ImmutableIterator<E> implements ListIterator<E> {
    
    /* -------------------------------------------------- List Iterator -------------------------------------------------- */
    
    private final ListIterator<E> listIterator;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ImmutableListIterator(ListIterator<E> iterator) {
        super(iterator);
        
        this.listIterator = Objects.requireNonNull(iterator);
    }
    
    /**
     * Returns an immutable list iterator that captures the given list iterator.
     */
    public static <E> ImmutableListIterator<E> with(ListIterator<E> iterator) {
        return new ImmutableListIterator<>(iterator);
    }
    
    /* -------------------------------------------------- Delegated Operations -------------------------------------------------- */
    
    @Override
    public boolean hasPrevious() {
        return listIterator.hasPrevious();
    }
    
    @Override
    public E previous() {
        return listIterator.previous();
    }
    
    @Override
    public int nextIndex() {
        return listIterator.nextIndex();
    }
    
    @Override
    public int previousIndex() {
        return listIterator.previousIndex();
    }
    
    /* -------------------------------------------------- Unsupported Operations -------------------------------------------------- */
    
    @Override
    public final void set(E e) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final void add(E e) {
        throw new UnsupportedOperationException();
    }
    
}
