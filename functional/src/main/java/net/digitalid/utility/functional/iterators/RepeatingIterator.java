package net.digitalid.utility.functional.iterators;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a repeating iterator that repeats the given element infinitely.
 */
@Mutable
public class RepeatingIterator<E> extends InfiniteIterator<E> {
    
    /* -------------------------------------------------- Element -------------------------------------------------- */
    
    protected final E element;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected RepeatingIterator(@Captured E element) {
        this.element = element;
    }
    
    /**
     * Returns a new repeating iterator that repeats the given element infinitely.
     */
    @Pure
    public static @Capturable <E> @Nonnull RepeatingIterator<E> with(@Captured E element) {
        return new RepeatingIterator<>(element);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Impure
    @Override
    public @NonCapturable E next() {
        return element;
    }
    
}
