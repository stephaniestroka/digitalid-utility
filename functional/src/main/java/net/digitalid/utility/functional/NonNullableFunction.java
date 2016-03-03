package net.digitalid.utility.functional;

import javax.annotation.Nonnull;

/**
 * Description.
 */
public interface NonNullableFunction<E1, E2> extends Function<E1, E2> {
    
    public @Nonnull E2 apply(@Nonnull E1 element);
    
}
