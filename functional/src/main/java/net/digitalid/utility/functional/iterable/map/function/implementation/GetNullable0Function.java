package net.digitalid.utility.functional.iterable.map.function.implementation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.iterable.map.function.NonNullToNullableUnaryFunction;
import net.digitalid.utility.tuples.pair.NullablePair;

/**
 * Provides an implementation to get the first element out of a tuple.
 */
public class GetNullable0Function<E0> extends NonNullToNullableUnaryFunction<NullablePair<E0, ?>, E0> {
    
    @Override
    public @Nullable E0 apply(@Nonnull NullablePair<E0, ?> element) {
        return element.get0();
    }
    
}
