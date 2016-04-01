package net.digitalid.utility.immutable.iterators;

import java.util.ListIterator;
import java.util.Objects;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.type.Mutable;

/**
 * This class implements an immutable list iterator.
 */
@Mutable
public class ImmutableListIterator<E> extends ImmutableIterator<E> implements ListIterator<E> {
    
    /* -------------------------------------------------- List Iterator -------------------------------------------------- */
    
    protected final @Nonnull ListIterator<E> listIterator;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ImmutableListIterator(@Captured @Nonnull ListIterator<E> iterator) {
        super(iterator);
        
        this.listIterator = Objects.requireNonNull(iterator);
    }
    
    /**
     * Returns an immutable list iterator that captures the given list iterator.
     */
    @Pure
    public static <E> @Capturable @Nonnull ImmutableListIterator<E> with(@Captured @Nonnull ListIterator<E> iterator) {
        return new ImmutableListIterator<>(iterator);
    }
    
    /* -------------------------------------------------- Delegated Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean hasPrevious() {
        return listIterator.hasPrevious();
    }
    
    @Impure
    @Override
    public E previous() {
        return listIterator.previous();
    }
    
    @Pure
    @Override
    public int nextIndex() {
        return listIterator.nextIndex();
    }
    
    @Pure
    @Override
    public int previousIndex() {
        return listIterator.previousIndex();
    }
    
    /* -------------------------------------------------- Unsupported Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public final void set(@NonCaptured @Unmodified E e) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final void add(@NonCaptured @Unmodified E e) {
        throw new UnsupportedOperationException();
    }
    
}
