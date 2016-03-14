package net.digitalid.utility.functional.iterable.map.function.implementation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.function.unary.NonNullToNullableUnaryFunction;
import net.digitalid.utility.tuples.pair.NullablePair;

/**
 * Provides an implementation to get the second element out of a tuple.
 */
public class GetNullable1Function<E1> extends NonNullToNullableUnaryFunction<NullablePair<?, E1>, E1> {
    
    @Override
    public @Nullable E1 apply(@Nonnull NullablePair<?, E1> element) {
        return element.get1();
    }
    
}
