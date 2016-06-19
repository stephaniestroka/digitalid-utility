package net.digitalid.utility.functional.interfaces;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.failable.FailableBinaryFunction;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a binary function that maps an object of type {@code I0} and an object of type {@code I1} to an object of type {@code O}.
 */
@Immutable
@Functional
public interface BinaryFunction<I0, I1, O> extends FailableBinaryFunction<I0, I1, O, RuntimeException> {
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of this function followed by the given function.
     */
    @Pure
    public default <T> @Nonnull BinaryFunction<I0, I1, T> before(@Nonnull UnaryFunction<? super O, ? extends T> function) {
        return (object0, object1) -> function.evaluate(evaluate(object0, object1));
    }
    
    /**
     * Returns the composition of the given functions followed by this function.
     */
    @Pure
    public default <T0, T1> @Nonnull BinaryFunction<T0, T1, O> after(@Nonnull UnaryFunction<? super T0, ? extends I0> function0, @Nonnull UnaryFunction<? super T1, ? extends I1> function1) {
        return (object0, object1) -> evaluate(function0.evaluate(object0), function1.evaluate(object1));
    }
    
    /* -------------------------------------------------- Null Handling -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull BinaryFunction<@Nullable I0, @Nullable I1, O> replaceNull(@Captured O defaultValue) {
        return (object0, object1) -> object0 != null && object1 != null ? evaluate(object0, object1) : defaultValue;
    }
    
    @Pure
    @Override
    public default @Nonnull BinaryFunction<@Nullable I0, @Nullable I1, @Nullable O> propagateNull() {
        return replaceNull(null);
    }
    
    /* -------------------------------------------------- Constant -------------------------------------------------- */
    
    /**
     * Returns a binary function that always returns the given object.
     */
    @Pure
    public static <O> @Nonnull BinaryFunction<@Nullable Object, @Nullable Object, O> constant(@Captured O object) {
        return (object0, object1) -> object;
    }
    
}
