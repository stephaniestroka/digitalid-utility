package net.digitalid.utility.tuples.triplet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class enables the creation of triplets.
 */
@Utility
public class Triplet {
    
    /**
     * Returns a triplet with the given nullable elements.
     */
    @Pure
    public static @Nonnull <E0, E1, E2> NullableTriplet<E0, E1, E2> withNullable(@Nullable E0 element0, @Nullable E1 element1, @Nullable E2 element2) {
        return new NullableTripletImplementation<>(element0, element1, element2);
    }
    
    /**
     * Returns a triplet with the given non-nullable elements.
     */
    @Pure
    public static @Nonnull <E0, E1, E2> NonNullableTriplet<E0, E1, E2> withNonNullable(@Nonnull E0 element0, @Nonnull E1 element1, @Nonnull E2 element2) {
        return new NonNullableTripletImplementation<>(element0, element1, element2);
    }
    
}
