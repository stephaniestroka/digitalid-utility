package net.digitalid.utility.functional.iterators;

import java.util.Iterator;
import java.util.Objects;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.type.Mutable;

/**
 * This class implements a read-only iterable iterator.
 */
@Mutable
public class ReadOnlyIterableIterator<E> extends ReadOnlyIterator<E> {
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    protected final @Nonnull Iterator<? extends E> iterator;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ReadOnlyIterableIterator(@Captured @Nonnull Iterator<? extends E> iterator) {
        this.iterator = Objects.requireNonNull(iterator);
    }
    
    /**
     * Returns a read-only iterable iterator that captures the given iterator.
     */
    @Pure
    public static <E> @Capturable @Nonnull ReadOnlyIterableIterator<E> with(@Captured @Nonnull Iterator<? extends E> iterator) {
        return new ReadOnlyIterableIterator<>(iterator);
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
    
}
