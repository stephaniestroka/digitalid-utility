package net.digitalid.utility.functional.function.unary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * Maps nullable elements to a non-null results by applying the implemented function.
 */
@Stateless
public abstract class NullableToNonNullUnaryFunction<I, O> implements ToNonNullUnaryFunction<I, O> {
    
    /**
     * Applies the function on a nullable element to produce a non-null result.
     */
    @Pure
    @Override
    public abstract @Nonnull O apply(@Nullable I element);
    
}
