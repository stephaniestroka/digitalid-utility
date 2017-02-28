package net.digitalid.utility.functional.interfaces;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.failable.FailableBinaryFunction;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a binary function that maps an object of type {@code INPUT0} and an object of type {@code INPUT1} to an object of type {@code OUTPUT}.
 */
@Immutable
@Functional
public interface BinaryFunction<@Specifiable INPUT0, @Specifiable INPUT1, @Specifiable OUTPUT> extends FailableBinaryFunction<INPUT0, INPUT1, OUTPUT, RuntimeException> {
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of this function followed by the given function.
     */
    @Pure
    public default <@Specifiable FINAL_OUTPUT> @Nonnull BinaryFunction<INPUT0, INPUT1, FINAL_OUTPUT> before(@Nonnull UnaryFunction<? super OUTPUT, ? extends FINAL_OUTPUT> function) {
        return (input0, input1) -> function.evaluate(evaluate(input0, input1));
    }
    
    /**
     * Returns the composition of the given functions followed by this function.
     */
    @Pure
    public default <@Specifiable INITIAL_INPUT0, @Specifiable INITIAL_INPUT1> @Nonnull BinaryFunction<INITIAL_INPUT0, INITIAL_INPUT1, OUTPUT> after(@Nonnull UnaryFunction<? super INITIAL_INPUT0, ? extends INPUT0> function0, @Nonnull UnaryFunction<? super INITIAL_INPUT1, ? extends INPUT1> function1) {
        return (input0, input1) -> evaluate(function0.evaluate(input0), function1.evaluate(input1));
    }
    
    /* -------------------------------------------------- Null Handling -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull BinaryFunction<@Nullable INPUT0, @Nullable INPUT1, OUTPUT> replaceNull(@Captured OUTPUT defaultValue) {
        return (input0, input1) -> input0 != null && input1 != null ? evaluate(input0, input1) : defaultValue;
    }
    
    @Pure
    @Override
    public default @Nonnull BinaryFunction<@Nullable INPUT0, @Nullable INPUT1, @Nullable OUTPUT> propagateNull() {
        return replaceNull(null);
    }
    
    /* -------------------------------------------------- Constant -------------------------------------------------- */
    
    /**
     * Returns a binary function that always returns the given output.
     */
    @Pure
    public static <@Specifiable OUTPUT> @Nonnull BinaryFunction<@Nullable Object, @Nullable Object, OUTPUT> constant(@Captured OUTPUT output) {
        return (input0, input1) -> output;
    }
    
}
