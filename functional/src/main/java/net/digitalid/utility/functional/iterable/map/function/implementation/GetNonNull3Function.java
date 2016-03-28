package net.digitalid.utility.functional.iterable.map.function.implementation;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.function.unary.NonNullToNonNullUnaryFunction;
import net.digitalid.utility.tupless.quartet.NonNullableQuartet;

/**
 * Provides an implementation to get the forth element out of a tuple.
 */
public class GetNonNull3Function<E3> extends NonNullToNonNullUnaryFunction<NonNullableQuartet<?, ?, ?, E3>, E3> {
    
    @Override
    public @Nonnull E3 apply(@Nonnull NonNullableQuartet<?, ?, ?, E3> element) {
        return element.get3();
    }
    
}
