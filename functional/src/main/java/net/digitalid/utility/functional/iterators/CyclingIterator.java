package net.digitalid.utility.functional.iterators;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a cycling iterator that iterates over the elements of the given iterable indefinitely.
 */
@Mutable
public class CyclingIterator<E> extends ReadOnlyIterator<E> {
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    protected final @Nonnull FiniteIterable<? extends E> iterable;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected CyclingIterator(@Nonnull FiniteIterable<? extends E> iterable) {
        this.iterable = iterable;
        this.iterator = iterable.iterator();
        this.hasNext = iterator.hasNext();
    }
    
    /**
     * Returns a new cycling iterator that iterates over the elements of the given iterable indefinitely.
     */
    @Pure
    public static @Capturable <E> @Nonnull CyclingIterator<E> with(@Nonnull FiniteIterable<? extends E> iterable) {
        return new CyclingIterator<>(iterable);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    private @Nonnull Iterator<? extends E> iterator;
    
    private final boolean hasNext;
    
    @Pure
    @Override
    public boolean hasNext() {
        return hasNext;
    }
    
    @Impure
    @Override
    public @NonCapturable E next() {
        if (!iterator.hasNext()) { iterator = iterable.iterator(); }
        return iterator.next();
    }
    
}
