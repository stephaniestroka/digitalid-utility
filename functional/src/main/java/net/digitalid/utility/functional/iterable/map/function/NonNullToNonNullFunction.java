package net.digitalid.utility.functional.iterable.map.function;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * Maps non-null elements to a non-null result by applying the implemented function.
 */
@Stateless
public abstract class NonNullToNonNullFunction<I, O> implements ToNonNullFunction<I, O> {
    
    /**
     * Applies the function on non-null elements to produce a non-null result.
     */
    @Pure
    @Override
    public abstract @Nonnull O apply(@Nonnull I... element);
    
}
