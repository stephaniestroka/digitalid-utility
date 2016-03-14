package net.digitalid.utility.tuples.quartet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class enables the creation of quartets.
 */
@Utility
public class Quartet {
    
    /**
     * Returns a quartet with the given nullable elements.
     */
    @Pure
    public static @Nonnull <E0, E1, E2, E3> NullableQuartet<E0, E1, E2, E3> withNullable(@Nullable E0 element0, @Nullable E1 element1, @Nullable E2 element2, @Nullable E3 element3) {
        return new NullableQuartetImplementation<>(element0, element1, element2, element3);
    }
    
    /**
     * Returns a quartet with the given non-nullable elements.
     */
    @Pure
    public static @Nonnull <E0, E1, E2, E3> NonNullableQuartet<E0, E1, E2, E3> withNonNullable(@Nonnull E0 element0, @Nonnull E1 element1, @Nonnull E2 element2, @Nonnull E3 element3) {
        return new NonNullableQuartetImplementation<>(element0, element1, element2, element3);
    }
    
}
