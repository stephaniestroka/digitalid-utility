package net.digitalid.utility.tuples.pair;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This interface models a pair with non-nullable elements.
 * 
 * @see NonNullablePairImplementation
 * @see NonNullableTriplet
 */
@Immutable
public interface NonNullablePair<E0, E1> extends NullablePair<E0, E1> {
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E0 get0();
    
    /**
     * Returns a new tuple with the first element set to the given object.
     */
    @Pure
    public @Nonnull NonNullablePair<E0, E1> setNonNullable0(@Nonnull E0 element0);
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E1 get1();
    
    /**
     * Returns a new tuple with the second element set to the given object.
     */
    @Pure
    public @Nonnull NonNullablePair<E0, E1> setNonNullable1(@Nonnull E1 element1);
    
}
