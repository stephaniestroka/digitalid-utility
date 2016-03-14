package net.digitalid.utility.functional.iterable.zip.function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * Maps non-null elements to a nullable result by applying the implemented function.
 */
@Stateless
public abstract class NonNullToNullableBinaryFunction<E0, E1, O> implements ToNullableBinaryFunction<E0, E1, O> {
    
    /**
     * Applies the function on non-null elements to produce a nullable result.
     */
    @Pure
    @Override
    public abstract @Nullable O apply(@Nonnull E0 element0, @Nonnull E1 element1);
    
}
