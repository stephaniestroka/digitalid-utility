package net.digitalid.utility.functional.iterable.map.function.implementation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.function.unary.NonNullToNullableUnaryFunction;
import net.digitalid.utility.tuples.NullablePair;
import net.digitalid.utility.tupless.triplet.NullableTriplet;

/**
 * Provides an implementation to get the third element out of a tuple.
 */
public class GetNullable2Function<E2> extends NonNullToNullableUnaryFunction<NullableTriplet<?, ?, E2>, E2> {
    
    @Override
    public @Nullable E2 apply(@Nonnull NullableTriplet<?, ?, E2> element) {
        return element.get2();
    }
    
}
