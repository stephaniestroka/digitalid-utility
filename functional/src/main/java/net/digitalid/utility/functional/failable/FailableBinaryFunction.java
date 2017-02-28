package net.digitalid.utility.functional.failable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.functional.interfaces.BinaryFunction;
import net.digitalid.utility.functional.interfaces.Consumer;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a failable binary function that maps an object of type {@code INPUT0} and an object of type {@code INPUT1} to an object of type {@code OUTPUT}.
 */
@Immutable
@Functional
public interface FailableBinaryFunction<@Specifiable INPUT0, @Specifiable INPUT1, @Specifiable OUTPUT, @Unspecifiable EXCEPTION extends Exception> {
    
    /* -------------------------------------------------- Evaluation -------------------------------------------------- */
    
    /**
     * Evaluates this function for the given objects.
     * All implementations of this method have to be side-effect-free.
     */
    @Pure
    public OUTPUT evaluate(@NonCaptured @Unmodified INPUT0 input0, @NonCaptured @Unmodified INPUT1 input1) throws EXCEPTION;
    
    /**
     * Evaluates this function for the objects in the given pair.
     */
    @Pure
    public default OUTPUT evaluate(@Nonnull Pair<INPUT0, INPUT1> pair) throws EXCEPTION {
        return evaluate(pair.get0(), pair.get1());
    }
    
    /* -------------------------------------------------- Suppression -------------------------------------------------- */
    
    /**
     * Returns a function that catches the exceptions of this function, passes them to the given exception handler and returns the given default output instead.
     */
    @Pure
    public default @Nonnull BinaryFunction<INPUT0, INPUT1, OUTPUT> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler, @Captured OUTPUT defaultOutput) {
        return (input0, input1) -> {
            try {
                return evaluate(input0, input1);
            } catch (@Nonnull Exception exception) {
                handler.consume(exception);
                return defaultOutput;
            }
        };
    }
    
    /**
     * Returns a function that catches the exceptions of this function, passes them to the given exception handler and returns null instead.
     */
    @Pure
    public default @Nonnull BinaryFunction<INPUT0, INPUT1, @Nullable OUTPUT> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler) {
        return suppressExceptions(handler, null);
    }
    
    /**
     * Returns a function that suppresses the exceptions of this function and returns the given default output instead.
     */
    @Pure
    public default @Nonnull BinaryFunction<INPUT0, INPUT1, OUTPUT> suppressExceptions(@Captured OUTPUT defaultOutput) {
        return suppressExceptions(Consumer.DO_NOTHING, defaultOutput);
    }
    
    /**
     * Returns a function that suppresses the exceptions of this function and returns null instead.
     */
    @Pure
    public default @Nonnull BinaryFunction<INPUT0, INPUT1, @Nullable OUTPUT> suppressExceptions() {
        return suppressExceptions(Consumer.DO_NOTHING, null);
    }
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of the given functions with a flexible exception type.
     */
    @Pure
    public static <@Specifiable INPUT0, @Specifiable INPUT1, @Specifiable INTERMEDIATE, @Specifiable OUTPUT, @Unspecifiable EXCEPTION extends Exception> @Nonnull FailableBinaryFunction<INPUT0, INPUT1, OUTPUT, EXCEPTION> compose(@Nonnull FailableBinaryFunction<? super INPUT0, ? super INPUT1, ? extends INTERMEDIATE, ? extends EXCEPTION> binaryFunction, @Nonnull FailableUnaryFunction<? super INTERMEDIATE, ? extends OUTPUT, ? extends EXCEPTION> unaryFunction) {
        return (input0, input1) -> unaryFunction.evaluate(binaryFunction.evaluate(input0, input1));
    }
    
    /**
     * Returns the composition of this function followed by the given function.
     * Unfortunately, it is not possible to make the exception type flexible.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableBinaryFunction, net.digitalid.utility.functional.failable.FailableUnaryFunction)
     */
    @Pure
    public default <@Specifiable FINAL_OUTPUT> @Nonnull FailableBinaryFunction<INPUT0, INPUT1, FINAL_OUTPUT, EXCEPTION> before(@Nonnull FailableUnaryFunction<? super OUTPUT, ? extends FINAL_OUTPUT, ? extends EXCEPTION> function) {
        return (input0, input1) -> function.evaluate(evaluate(input0, input1));
    }
    
    /**
     * Returns the composition of the given functions with a flexible exception type.
     */
    @Pure
    public static <@Specifiable INPUT0, @Specifiable INPUT1, @Specifiable INTERMEDIATE0, @Specifiable INTERMEDIATE1, @Specifiable OUTPUT, @Unspecifiable EXCEPTION extends Exception> @Nonnull FailableBinaryFunction<INPUT0, INPUT1, OUTPUT, EXCEPTION> compose(@Nonnull FailableUnaryFunction<? super INPUT0, ? extends INTERMEDIATE0, ? extends EXCEPTION> unaryFunction0, @Nonnull FailableUnaryFunction<? super INPUT1, ? extends INTERMEDIATE1, ? extends EXCEPTION> unaryFunction1, @Nonnull FailableBinaryFunction<? super INTERMEDIATE0, ? super INTERMEDIATE1, ? extends OUTPUT, ? extends EXCEPTION> binaryFunction) {
        return (input0, input1) -> binaryFunction.evaluate(unaryFunction0.evaluate(input0), unaryFunction1.evaluate(input1));
    }
    
    /**
     * Returns the composition of the given functions followed by this function.
     * Unfortunately, it is not possible to make the exception type flexible.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableUnaryFunction, net.digitalid.utility.functional.failable.FailableUnaryFunction, net.digitalid.utility.functional.failable.FailableBinaryFunction)
     */
    @Pure
    public default <@Specifiable INITIAL_INPUT0, @Specifiable INITIAL_INPUT1> @Nonnull FailableBinaryFunction<INITIAL_INPUT0, INITIAL_INPUT1, OUTPUT, EXCEPTION> after(@Nonnull FailableUnaryFunction<? super INITIAL_INPUT0, ? extends INPUT0, ? extends EXCEPTION> function0, @Nonnull FailableUnaryFunction<? super INITIAL_INPUT1, ? extends INPUT1, ? extends EXCEPTION> function1) {
        return (input0, input1) -> evaluate(function0.evaluate(input0), function1.evaluate(input1));
    }
    
    /* -------------------------------------------------- Null Handling -------------------------------------------------- */
    
    /**
     * Returns a new function based on this function that returns the given default output if either input is null.
     */
    @Pure
    public default @Nonnull FailableBinaryFunction<@Nullable INPUT0, @Nullable INPUT1, OUTPUT, EXCEPTION> replaceNull(@Captured OUTPUT defaultOutput) {
        return (input0, input1) -> input0 != null && input1 != null ? evaluate(input0, input1) : defaultOutput;
    }
    
    /**
     * Returns a new function based on this function that propagates null instead of evaluating it.
     */
    @Pure
    public default @Nonnull FailableBinaryFunction<@Nullable INPUT0, @Nullable INPUT1, @Nullable OUTPUT, EXCEPTION> propagateNull() {
        return replaceNull(null);
    }
    
}
