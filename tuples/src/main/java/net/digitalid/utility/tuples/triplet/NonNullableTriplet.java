package net.digitalid.utility.tuples.triplet;

import javax.annotation.Nonnull;

import net.digitalid.utility.tuples.pair.NonNullablePair;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This interface models a triplet with non-nullable elements.
 * 
 * @see NonNullableTripletImplementation
 * @see NonNullableQuartet
 */
@Immutable
public interface NonNullableTriplet<E0, E1, E2> extends NullableTriplet<E0, E1, E2>, NonNullablePair<E0, E1> {
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E0 get0();
    
    @Pure
    @Override
    public @Nonnull NonNullableTriplet<E0, E1, E2> setNonNullable0(@Nonnull E0 element0);
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E1 get1();
    
    @Pure
    @Override
    public @Nonnull NonNullableTriplet<E0, E1, E2> setNonNullable1(@Nonnull E1 element1);
    
    /* -------------------------------------------------- Element 2 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E2 get2();
    
    /**
     * Returns a new tuple with the third element set to the given object.
     */
    @Pure
    public @Nonnull NonNullableTriplet<E0, E1, E2> setNonNullable2(@Nonnull E2 element2);
    
}
