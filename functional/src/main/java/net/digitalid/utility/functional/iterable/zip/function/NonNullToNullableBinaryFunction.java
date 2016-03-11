package net.digitalid.utility.functional.iterable.zip.function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * Maps non-null elements to a nullable result by applying the implemented function.
 */
@Stateless
public abstract class NonNullToNullableBinaryFunction<I, O, A> implements ToNullableBinaryFunction<I, O, A> {
    
    /**
     * Applies the function on non-null elements to produce a nullable result.
     */
    @Pure
    @Override
    public abstract @Nullable O apply(@Nonnull I element1, @Nonnull I element2, @Nullable A additionalInformation);
    
}
