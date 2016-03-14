package net.digitalid.utility.functional.iterable.map.function;

import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * Maps nullable elements to a nullable result by applying the implemented function.
 */
@Stateless
public abstract class NullableToNullableUnaryFunction<I, O> implements ToNullableUnaryFunction<I, O> {
    
    /**
     * Applies the function on nullable elements to produce a nullable result.
     */
    @Pure
    @Override
    public abstract @Nullable O apply(@Nullable I element);
    
}
