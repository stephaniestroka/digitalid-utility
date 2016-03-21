package net.digitalid.utility.functional.iterable.map.function.implementation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.function.unary.NonNullToNonNullUnaryFunction;
import net.digitalid.utility.functional.function.unary.NonNullToNullableUnaryFunction;
import net.digitalid.utility.tuples.NonNullablePair;
import net.digitalid.utility.tuples.NullablePair;

/**
 * Provides an implementation to get the first element out of a tuple.
 */
public class GetNonNull0Function<E0, E1> extends NonNullToNonNullUnaryFunction<NonNullablePair<E0, E1>, E0> {
    
    @Override
    public @Nonnull E0 apply(@Nonnull NonNullablePair<E0, E1> element) {
        return element.get0();
    }
    
}
