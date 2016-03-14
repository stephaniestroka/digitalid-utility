package net.digitalid.utility.tuples.pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class enables the creation of pairs.
 */
@Utility
public class Pair {
    
    /**
     * Returns a pair with the given nullable elements.
     */
    @Pure
    public static @Nonnull <E0, E1> NullablePair<E0, E1> withNullable(@Nullable E0 element0, @Nullable E1 element1) {
        return new NullablePairImplementation<>(element0, element1);
    }
    
    /**
     * Returns a pair with the given non-nullable elements.
     */
    @Pure
    public static @Nonnull <E0, E1> NonNullablePair<E0, E1> withNonNullable(@Nonnull E0 element0, @Nonnull E1 element1) {
        return new NonNullablePairImplementation<>(element0, element1);
    }
    
}
