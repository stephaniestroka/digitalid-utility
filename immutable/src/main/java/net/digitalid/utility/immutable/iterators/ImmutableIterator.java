package net.digitalid.utility.immutable.iterators;

import java.util.Iterator;
import java.util.Objects;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.type.Mutable;

/**
 * This class implements an immutable iterator.
 */
@Mutable
public class ImmutableIterator<E> implements Iterator<E> {
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    protected final @Nonnull Iterator<E> iterator;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ImmutableIterator(@Captured @Nonnull Iterator<E> iterator) {
        this.iterator = Objects.requireNonNull(iterator);
    }
    
    /**
     * Returns an immutable iterator that captures the given iterator.
     */
    @Pure
    public static <E> @Capturable @Nonnull ImmutableIterator<E> with(@Captured @Nonnull Iterator<E> iterator) {
        return new ImmutableIterator<>(iterator);
    }
    
    /* -------------------------------------------------- Delegated Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }
    
    @Impure
    @Override
    public E next() {
        return iterator.next();
    }
    
    /* -------------------------------------------------- Unsupported Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public final void remove() {
        throw new UnsupportedOperationException();
    }
    
}
