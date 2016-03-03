package net.digitalid.utility.functional;

import javax.annotation.Nullable;

/**
 *
 */
public interface NullableFunction<E1, E2> extends Function<E1, E2> {
    
    public @Nullable E2 apply(@Nullable E1 element);
    
}
