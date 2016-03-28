package net.digitalid.utility.functional.iterable.map.function.implementation;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.function.unary.NonNullToNonNullUnaryFunction;
import net.digitalid.utility.tuples.NonNullablePair;
import net.digitalid.utility.tupless.triplet.NonNullableTriplet;

/**
 * Provides an implementation to get the third element out of a tuple.
 */
public class GetNonNull2Function<E2> extends NonNullToNonNullUnaryFunction<NonNullableTriplet<?, ?, E2>, E2> {
    
    @Override
    public @Nonnull E2 apply(@Nonnull NonNullableTriplet<?, ?, E2> element) {
        return element.get2();
    }
    
}
