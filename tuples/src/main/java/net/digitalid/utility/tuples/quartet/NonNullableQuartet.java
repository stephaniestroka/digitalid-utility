package net.digitalid.utility.tuples.quartet;

import javax.annotation.Nonnull;

import net.digitalid.utility.tuples.triplet.NonNullableTriplet;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This interface models a quartet with non-nullable elements.
 * 
 * @see NonNullableQuartetImplementation
 */
@Immutable
public interface NonNullableQuartet<E0, E1, E2, E3> extends NullableQuartet<E0, E1, E2, E3>, NonNullableTriplet<E0, E1, E2> {
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E0 get0();
    
    @Pure
    @Override
    public @Nonnull NonNullableQuartet<E0, E1, E2, E3> setNonNullable0(@Nonnull E0 element0);
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E1 get1();
    
    @Pure
    @Override
    public @Nonnull NonNullableQuartet<E0, E1, E2, E3> setNonNullable1(@Nonnull E1 element1);
    
    /* -------------------------------------------------- Element 2 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E2 get2();
    
    @Pure
    @Override
    public @Nonnull NonNullableQuartet<E0, E1, E2, E3> setNonNullable2(@Nonnull E2 element2);
    
    /* -------------------------------------------------- Element 3 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E3 get3();
    
    /**
     * Returns a new tuple with the fourth element set to the given object.
     */
    @Pure
    public @Nonnull NonNullableQuartet<E0, E1, E2, E3> setNonNullable3(@Nonnull E3 element3);
    
}
