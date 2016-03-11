package net.digitalid.utility.functional.iterable.zip.function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * Maps non-null elements to a non-null result by applying the implemented function.
 */
@Stateless
public abstract class NonNullToNonNullBinaryFunction<I, O, A> implements ToNonNullBinaryFunction<I, O, A> {
    
    /**
     * Applies the function on non-null elements to produce a non-null result.
     */
    @Pure
    @Override
    public abstract @Nonnull O apply(@Nonnull I element1, @Nonnull I element2, @Nullable A additionalInformation);
    
}
