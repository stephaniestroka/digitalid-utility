package net.digitalid.utility.tuples.quartet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.tuples.triplet.NullableTriplet;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class models a quartet with nullable elements.
 * 
 * @see NullableQuartetImplementation
 * @see NonNullableQuartet
 */
@Immutable
@SuppressWarnings("EqualsAndHashcode")
public interface NullableQuartet<E0, E1, E2, E3> extends NullableTriplet<E0, E1, E2> {
    
    /* -------------------------------------------------- Element 3 -------------------------------------------------- */
    
    /**
     * Returns the fourth element of this tuple.
     */
    @Pure
    public @Nullable E3 get3();
    
    /**
     * Returns a new tuple with the fourth element set to the given object.
     */
    @Pure
    public @Nonnull NullableQuartet<E0, E1, E2, E3> setNullable3(@Nullable E3 element3);
    
}
