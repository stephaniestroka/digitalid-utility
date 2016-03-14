package net.digitalid.utility.functional.iterable.map.function.implementation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.iterable.map.function.NonNullToNonNullUnaryFunction;
import net.digitalid.utility.functional.iterable.map.function.NonNullToNullableUnaryFunction;
import net.digitalid.utility.tuples.pair.NonNullablePair;
import net.digitalid.utility.tuples.pair.NullablePair;

/**
 * Provides an implementation to get the first element out of a tuple.
 */
public class GetNonNull0Function<E0> extends NonNullToNonNullUnaryFunction<NonNullablePair<E0, ?>, E0> {
    
    @Override
    public @Nonnull E0 apply(@Nonnull NonNullablePair<E0, ?> element) {
        return element.get0();
    }
    
}
