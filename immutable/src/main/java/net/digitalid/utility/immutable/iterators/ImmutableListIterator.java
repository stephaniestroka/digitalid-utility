package net.digitalid.utility.immutable.iterators;

import java.util.ListIterator;
import java.util.Objects;

/**
 * This class implements an immutable list iterator of non-nullable elements.
 * <p>
 * <em>Important:</em> This list iterator is only immutable in Java 1.7!
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
     * The given list iterator should only return non-nullable elements.
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
    public int nextIndex() {
        return listIterator.nextIndex();
    }
    
    @Override
    public int previousIndex() {
        return listIterator.previousIndex();
    }
    
    /* -------------------------------------------------- NonNullable Results -------------------------------------------------- */
    
    /**
     * {@inheritDoc}
     *
     * @ensure result != null : "The result may not be null.";
     */
    @Override
    public E previous() {
        return Objects.requireNonNull(listIterator.previous());
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
