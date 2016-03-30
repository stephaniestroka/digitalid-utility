package net.digitalid.utility.functional.iterators;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.reference.Capturable;
import net.digitalid.utility.annotations.type.Mutable;

/**
 * This class implements a repeating iterator that repeats the given object infinitely.
 */
@Mutable
public class RepeatingIterator<E> implements InfiniteIterator<E> {
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    protected final E object;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected RepeatingIterator(E object) {
        this.object = object;
    }
    
    /**
     * Returns a new repeating iterator that repeats the given object infinitely.
     */
    @Pure
    public static <E> @Capturable @Nonnull RepeatingIterator<E> with(E object) {
        return new RepeatingIterator<>(object);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Override
    public E next() {
        return object;
    }
    
}
