package net.digitalid.utility.functional.iterables;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Updating;

/**
 * This interface extends the finite iterable interface to provide a faster {@link #size()} implementation.
 * 
 * @see CollectionBasedIterable
 */
@Updating
@Functional
public interface CollectionIterable<E> extends FiniteIterable<E> {
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Pure
    @Override
    public default boolean isEmpty() {
        return size() == 0;
    }
    
    @Pure
    @Override
    public default @NonNegative int size(@Positive int limit) {
        return Math.min(size(), limit);
    }
    
    @Pure
    @Override
    public @NonNegative int size();
    
}
