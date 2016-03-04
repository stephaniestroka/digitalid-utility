package net.digitalid.utility.immutable.iterators;

import java.util.Iterator;
import java.util.Objects;

/**
 * This class implements an immutable iterator of non-nullable elements.
 * <p>
 * <em>Important:</em> This iterator is only immutable in Java 1.7!
 */
public class ImmutableIterator<E> implements Iterator<E> {
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    private final Iterator<E> iterator;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ImmutableIterator(Iterator<E> iterator) {
        this.iterator = Objects.requireNonNull(iterator);
    }
    
    /**
     * Returns an immutable iterator that captures the given iterator.
     * The given iterator should only return non-nullable elements.
     */
    public static <E> ImmutableIterator<E> with(Iterator<E> iterator) {
        return new ImmutableIterator<>(iterator);
    }
    
    /* -------------------------------------------------- Delegated Operations -------------------------------------------------- */
    
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }
    
    /* -------------------------------------------------- NonNullable Results -------------------------------------------------- */
    
    /**
     * {@inheritDoc}
     *
     * @ensure result != null : "The result may not be null.";
     */
    @Override
    public E next() {
        return Objects.requireNonNull(iterator.next());
    }
    
    /* -------------------------------------------------- Unsupported Operations -------------------------------------------------- */
    
    @Override
    public final void remove() {
        throw new UnsupportedOperationException();
    }
    
}
