package net.digitalid.utility.functional.iterable.zip.function;

import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * Maps nullable elements to a nullable result by applying the implemented function.
 */
@Stateless
public abstract class NullableToNullableBinaryFunction<I, O, A> implements ToNullableBinaryFunction<I, O, A> {
    
    /**
     * Applies the function on nullable elements to produce a nullable result.
     */
    @Pure
    @Override
    public abstract @Nullable O apply(@Nullable I element1, @Nullable I element2, @Nullable A additionalInformation);
    
}
