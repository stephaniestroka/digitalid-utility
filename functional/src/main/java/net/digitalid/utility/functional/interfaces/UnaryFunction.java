package net.digitalid.utility.functional.interfaces;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This functional interface models a unary function that maps an object of type {@code I} to an object of type {@code O}.
 */
public interface UnaryFunction<I, O> {
    
    /**
     * Evaluates this function for the given object.
     * All implementations of this method have to be side-effect-free.
     */
    @Pure
    public O evaluate(I object);
    
    /**
     * Returns the composition of this function followed by the given function.
     */
    @Pure
    public default <T> UnaryFunction<I, T> before(UnaryFunction<? super O, ? extends T> function) {
        return object -> function.evaluate(evaluate(object));
    }
    
    /**
     * Returns the composition of the given function followed by this function.
     */
    @Pure
    public default <T> UnaryFunction<T, O> after(UnaryFunction<? super T, ? extends I> function) {
        return object -> evaluate(function.evaluate(object));
    }
    
}
