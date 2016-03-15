package net.digitalid.utility.functional.function.unary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * Maps non-null elements to a nullable results by applying the implemented function.
 */
@Stateless
public abstract class NonNullToNullableUnaryFunction<I, O> implements ToNullableUnaryFunction<I, O> {
    
    /**
     * Applies the function on a non-null element to produce a nullable result.
     */
    @Pure
    @Override
    public abstract @Nullable O apply(@Nonnull I element);
    
}
