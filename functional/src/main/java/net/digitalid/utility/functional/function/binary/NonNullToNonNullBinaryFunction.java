package net.digitalid.utility.functional.function.binary;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * Maps non-null elements to a non-null result by applying the implemented function.
 */
@Stateless
public abstract class NonNullToNonNullBinaryFunction<E0, E1, O> implements ToNonNullBinaryFunction<E0, E1, O> {
    
    /**
     * Applies the function on non-null elements to produce a non-null result.
     */
    @Pure
    @Override
    public abstract @Nonnull O apply(@Nonnull E0 element0, @Nonnull E1 element1);
    
}