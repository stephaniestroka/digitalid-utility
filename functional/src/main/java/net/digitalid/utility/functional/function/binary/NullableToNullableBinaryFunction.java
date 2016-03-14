package net.digitalid.utility.functional.function.binary;

import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * Maps nullable elements to a nullable result by applying the implemented function.
 */
@Stateless
public abstract class NullableToNullableBinaryFunction<E0, E1, O> implements ToNullableBinaryFunction<E0, E1, O> {
    
    /**
     * Applies the function on nullable elements to produce a nullable result.
     */
    @Pure
    @Override
    public abstract @Nullable O apply(@Nullable E0 element0, @Nullable E1 element1);
    
}
