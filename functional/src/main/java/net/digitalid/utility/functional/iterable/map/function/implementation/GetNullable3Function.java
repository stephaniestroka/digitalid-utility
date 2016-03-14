package net.digitalid.utility.functional.iterable.map.function.implementation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.iterable.map.function.NonNullToNullableUnaryFunction;
import net.digitalid.utility.tuples.quartet.NullableQuartet;

/**
 * Provides an implementation to get the forth element out of a tuple.
 */
public class GetNullable3Function<E3> extends NonNullToNullableUnaryFunction<NullableQuartet<?, ?, ?, E3>, E3> {
    
    @Override
    public @Nullable E3 apply(@Nonnull NullableQuartet<?, ?, ?, E3> element) {
        return element.get3();
    }
    
}
