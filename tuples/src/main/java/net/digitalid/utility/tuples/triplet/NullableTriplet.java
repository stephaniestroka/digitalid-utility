package net.digitalid.utility.tuples.triplet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.tuples.pair.NullablePair;
import net.digitalid.utility.tuples.quartet.NullableQuartet;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This interface models a triplet with nullable elements.
 * 
 * @see NullableTripletImplementation
 * @see NonNullableTriplet
 * @see NullableQuartet
 */
@Immutable
public interface NullableTriplet<E0, E1, E2> extends NullablePair<E0, E1> {
    
    /* -------------------------------------------------- Element 2 -------------------------------------------------- */
    
    /**
     * Returns the third element of this tuple.
     */
    @Pure
    public @Nullable E2 get2();
    
    /**
     * Returns a new tuple with the third element set to the given object.
     */
    @Pure
    public @Nonnull NullableTriplet<E0, E1, E2> setNullable2(@Nullable E2 element2);
    
}
