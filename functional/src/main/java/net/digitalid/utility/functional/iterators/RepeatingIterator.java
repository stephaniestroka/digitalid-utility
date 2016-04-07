package net.digitalid.utility.functional.iterators;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.type.Mutable;

/**
 * This class implements a repeating iterator that repeats the given object infinitely.
 */
@Mutable
public class RepeatingIterator<E> extends InfiniteIterator<E> {
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    protected final E object;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected RepeatingIterator(@Captured E object) {
        this.object = object;
    }
    
    /**
     * Returns a new repeating iterator that repeats the given object infinitely.
     */
    @Pure
    public static <E> @Capturable @Nonnull RepeatingIterator<E> with(@Captured E object) {
        return new RepeatingIterator<>(object);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Impure
    @Override
    public @NonCapturable E next() {
        return object;
    }
    
}
