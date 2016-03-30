package net.digitalid.utility.functional.interfaces;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a unary function that maps an object of type {@code I} to an object of type {@code O}.
 */
@Immutable
@Functional
public interface UnaryFunction<I, O> {
    
    /* -------------------------------------------------- Evaluation -------------------------------------------------- */
    
    /**
     * Evaluates this function for the given object.
     * All implementations of this method have to be side-effect-free.
     */
    @Pure
    public O evaluate(I object);
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of this function followed by the given function.
     */
    @Pure
    public default <T> @Nonnull UnaryFunction<I, T> before(@Nonnull UnaryFunction<? super O, ? extends T> function) {
        return object -> function.evaluate(evaluate(object));
    }
    
    /**
     * Returns the composition of the given function followed by this function.
     */
    @Pure
    public default <T> @Nonnull UnaryFunction<T, O> after(@Nonnull UnaryFunction<? super T, ? extends I> function) {
        return object -> evaluate(function.evaluate(object));
    }
    
}
