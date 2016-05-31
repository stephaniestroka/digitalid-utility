package net.digitalid.utility.functional.failable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
 * This functional interface models a failable binary function that maps an object of type {@code I0} and an object of type {@code I1} to an object of type {@code O}.
 */
@Immutable
@Functional
public interface FailableBinaryFunction<I0, I1, O, X extends Exception> {
    
    /* -------------------------------------------------- Evaluation -------------------------------------------------- */
    
    /**
     * Evaluates this function for the given objects.
     * All implementations of this method have to be side-effect-free.
     */
    @Pure
    public O evaluate(@NonCaptured @Unmodified I0 object0, @NonCaptured @Unmodified I1 object1) throws X;
    
    /**
     * Evaluates this function for the objects in the given pair.
     */
    @Pure
    public default O evaluate(@Nonnull Pair<I0, I1> pair) throws X {
        return evaluate(pair.get0(), pair.get1());
    }
    
    /* -------------------------------------------------- Suppression -------------------------------------------------- */
    
    /**
     * Returns a function that catches the exceptions of this function, passes them to the given exception handler and returns the given default output instead.
     */
    @Pure
    public default @Nonnull BinaryFunction<I0, I1, O> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler, @Captured O defaultOutput) {
        return (object0, object1) -> {
            try {
                return evaluate(object0, object1);
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
    public default @Nonnull BinaryFunction<I0, I1, @Nullable O> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler) {
        return suppressExceptions(handler, null);
    }
    
    /**
     * Returns a function that suppresses the exceptions of this function and returns the given default output instead.
     */
    @Pure
    public default @Nonnull BinaryFunction<I0, I1, O> suppressExceptions(@Captured O defaultOutput) {
        return suppressExceptions(Consumer.DO_NOTHING, defaultOutput);
    }
    
    /**
     * Returns a function that suppresses the exceptions of this function and returns null instead.
     */
    @Pure
    public default @Nonnull BinaryFunction<I0, I1, @Nullable O> suppressExceptions() {
        return suppressExceptions(Consumer.DO_NOTHING, null);
    }
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of the given functions with a flexible exception type.
     */
    @Pure
    public static <I0, I1, O, T, X extends Exception> @Nonnull FailableBinaryFunction<I0, I1, O, X> compose(@Nonnull FailableBinaryFunction<? super I0, ? super I1, ? extends T, ? extends X> binaryFunction, @Nonnull FailableUnaryFunction<? super T, ? extends O, ? extends X> unaryFunction) {
        return (object0, object1) -> unaryFunction.evaluate(binaryFunction.evaluate(object0, object1));
    }
    
    /**
     * Returns the composition of this function followed by the given function.
     * Unfortunately, it is not possible to make the exception type flexible.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableBinaryFunction, net.digitalid.utility.functional.failable.FailableUnaryFunction)
     */
    @Pure
    public default <T> @Nonnull FailableBinaryFunction<I0, I1, T, X> before(@Nonnull FailableUnaryFunction<? super O, ? extends T, ? extends X> function) {
        return (object0, object1) -> function.evaluate(evaluate(object0, object1));
    }
    
    /**
     * Returns the composition of the given functions with a flexible exception type.
     */
    @Pure
    public static <I0, I1, O, T0, T1, X extends Exception> @Nonnull FailableBinaryFunction<I0, I1, O, X> compose(@Nonnull FailableUnaryFunction<? super I0, ? extends T0, ? extends X> unaryFunction0, @Nonnull FailableUnaryFunction<? super I1, ? extends T1, ? extends X> unaryFunction1, @Nonnull FailableBinaryFunction<? super T0, ? super T1, ? extends O, ? extends X> binaryFunction) {
        return (object0, object1) -> binaryFunction.evaluate(unaryFunction0.evaluate(object0), unaryFunction1.evaluate(object1));
    }
    
    /**
     * Returns the composition of the given functions followed by this function.
     * Unfortunately, it is not possible to make the exception type flexible.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableUnaryFunction, net.digitalid.utility.functional.failable.FailableUnaryFunction, net.digitalid.utility.functional.failable.FailableBinaryFunction)
     */
    @Pure
    public default <T0, T1> @Nonnull FailableBinaryFunction<T0, T1, O, X> after(@Nonnull FailableUnaryFunction<? super T0, ? extends I0, ? extends X> function0, @Nonnull FailableUnaryFunction<? super T1, ? extends I1, ? extends X> function1) {
        return (object0, object1) -> evaluate(function0.evaluate(object0), function1.evaluate(object1));
    }
    
    /* -------------------------------------------------- Null Handling -------------------------------------------------- */
    
    /**
     * Returns a new function based on this function that returns the given default output if either input is null.
     */
    @Pure
    public default @Nonnull FailableBinaryFunction<@Nullable I0, @Nullable I1, O, X> replaceNull(@Captured O defaultOutput) {
        return (object0, object1) -> object0 != null && object1 != null ? evaluate(object0, object1) : defaultOutput;
    }
    
    /**
     * Returns a new function based on this function that propagates null instead of evaluating it.
     */
    @Pure
    public default @Nonnull FailableBinaryFunction<@Nullable I0, @Nullable I1, @Nullable O, X> propagateNull() {
        return replaceNull(null);
    }
    
}
