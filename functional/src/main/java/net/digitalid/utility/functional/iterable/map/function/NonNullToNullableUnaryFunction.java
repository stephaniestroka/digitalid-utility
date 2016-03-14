package net.digitalid.utility.functional.iterable.map.function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * Maps non-null elements to a nullable result by applying the implemented function.
 */
@Stateless
public abstract class NonNullToNullableUnaryFunction<I, O> implements ToNullableUnaryFunction<I, O> {
    
    /**
     * Applies the function on non-null elements to produce a nullable result.
     */
    @Pure
    @Override
    public abstract @Nullable O apply(@Nonnull I element);
    
}
