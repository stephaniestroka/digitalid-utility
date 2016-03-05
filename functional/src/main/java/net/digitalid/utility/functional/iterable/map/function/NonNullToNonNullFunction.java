package net.digitalid.utility.functional.iterable.map.function;

import javax.annotation.Nonnull;

/**
 * Maps non-null elements to a non-null result by applying the implemented function.
 */
public abstract class NonNullToNonNullFunction<I, O> implements Function<I, O> {
    
    /**
     * Applies the function on non-null elements to produce a non-null result.
     */
    @Override
    public abstract @Nonnull O apply(@Nonnull I... element);
    
}
