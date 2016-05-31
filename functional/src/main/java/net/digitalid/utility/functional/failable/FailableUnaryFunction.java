package net.digitalid.utility.functional.failable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.functional.interfaces.Consumer;
import net.digitalid.utility.functional.interfaces.UnaryFunction;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a failable unary function that maps an object of type {@code I} to an object of type {@code O}.
 */
@Immutable
@Functional
public interface FailableUnaryFunction<I, O, X extends Exception> {
    
    /* -------------------------------------------------- Evaluation -------------------------------------------------- */
    
    /**
     * Evaluates this function for the given object.
     * All implementations of this method have to be side-effect-free.
     */
    @Pure
    public O evaluate(@NonCaptured @Unmodified I object) throws X;
    
    /* -------------------------------------------------- Suppression -------------------------------------------------- */
    
    /**
     * Returns a function that catches the exceptions of this function, passes them to the given exception handler and returns the given default output instead.
     */
    @Pure
    public default @Nonnull UnaryFunction<I, O> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler, @Captured O defaultOutput) {
        return object -> {
            try {
                return evaluate(object);
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
    public default @Nonnull UnaryFunction<I, @Nullable O> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler) {
        return suppressExceptions(handler, null);
    }
    
    /**
     * Returns a function that suppresses the exceptions of this function and returns the given default output instead.
     */
    @Pure
    public default @Nonnull UnaryFunction<I, O> suppressExceptions(@Captured O defaultOutput) {
        return suppressExceptions(Consumer.DO_NOTHING, defaultOutput);
    }
    
    /**
     * Returns a function that suppresses the exceptions of this function and returns null instead.
     */
    @Pure
    public default @Nonnull UnaryFunction<I, @Nullable O> suppressExceptions() {
        return suppressExceptions(Consumer.DO_NOTHING, null);
    }
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of the given functions with a flexible exception type.
     */
    @Pure
    public static <I, O, T, X extends Exception> @Nonnull FailableUnaryFunction<I, O, X> compose(@Nonnull FailableUnaryFunction<? super I, ? extends T, ? extends X> function0, @Nonnull FailableUnaryFunction<? super T, ? extends O, ? extends X> function1) {
        return object -> function1.evaluate(function0.evaluate(object));
    }
    
    /**
     * Returns the composition of this function followed by the given function.
     * Unfortunately, it is not possible to make the exception type flexible.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableUnaryFunction, net.digitalid.utility.functional.failable.FailableUnaryFunction)
     */
    @Pure
    public default <T> @Nonnull FailableUnaryFunction<I, T, X> before(@Nonnull FailableUnaryFunction<? super O, ? extends T, ? extends X> function) {
        return object -> function.evaluate(evaluate(object));
    }
    
    /**
     * Returns the composition of the given function followed by this function.
     * Unfortunately, it is not possible to make the exception type flexible.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableUnaryFunction, net.digitalid.utility.functional.failable.FailableUnaryFunction)
     */
    @Pure
    public default <T> @Nonnull FailableUnaryFunction<T, O, X> after(@Nonnull FailableUnaryFunction<? super T, ? extends I, ? extends X> function) {
        return object -> evaluate(function.evaluate(object));
    }
    
    /* -------------------------------------------------- Null Handling -------------------------------------------------- */
    
    /**
     * Returns a new function based on this function that returns the given default output for null.
     */
    @Pure
    public default @Nonnull FailableUnaryFunction<@Nullable I, O, X> replaceNull(@Captured O defaultOutput) {
        return object -> object != null ? evaluate(object) : defaultOutput;
    }
    
    /**
     * Returns a new function based on this function that propagates null instead of evaluating it.
     */
    @Pure
    public default @Nonnull FailableUnaryFunction<@Nullable I, @Nullable O, X> propagateNull() {
        return replaceNull(null);
    }
    
}
