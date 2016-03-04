package net.digitalid.utility.functional.iterable.map.function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Maps non-null elements to a nullable result by applying the implemented function.
 */
public abstract class NonNullToNullableFunction<I, O> implements Function<I, O> {
    
    /**
     * Applies the function on non-null elements to produce a nullable result.
     */
    @Override
    public abstract @Nullable O apply(@Nonnull I... element);
    
}
