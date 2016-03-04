package net.digitalid.utility.functional.iterable.map.function;

import javax.annotation.Nullable;

/**
 * Maps nullable elements to a nullable result by applying the implemented function.
 */
public abstract class NullableToNullableFunction<I, O> implements Function<I, O> {
    
    /**
     * Applies the function on nullable elements to produce a nullable result.
     */
    @Override
    public abstract @Nullable O apply(@Nullable I... element);
    
}
