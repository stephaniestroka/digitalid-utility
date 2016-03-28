package net.digitalid.utility.functional.iterable.map.function.implementation;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.function.unary.NonNullToNonNullUnaryFunction;
import net.digitalid.utility.tuples.NonNullablePair;

/**
 * Provides an implementation to get the second element out of a tuple.
 */
public class GetNonNull1Function<E1> extends NonNullToNonNullUnaryFunction<NonNullablePair<?, E1>, E1> {
    
    @Override
    public @Nonnull E1 apply(@Nonnull NonNullablePair<?, E1> element) {
        return element.get1();
    }
    
}
