package net.digitalid.utility.functional.interfaces;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This functional interface models a method that produces objects of type {@code T} without requiring a parameter.
 */
public interface Producer<T> {
    
    /* -------------------------------------------------- Production -------------------------------------------------- */
    
    /**
     * Produces a result.
     */
    public T produce();
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of this producer followed by the given function.
     */
    @Pure
    public default <O> Producer<O> after(UnaryFunction<? super T, ? extends O> function) {
        return () -> function.evaluate(produce());
    }
    
    /* -------------------------------------------------- Conversion -------------------------------------------------- */
    
    /**
     * Returns this producer as a unary function that ignores its input.
     * This method may only be called if this producer is side-effect-free.
     */
    @Pure
    public default UnaryFunction<Object, T> asFunction() {
        return object -> produce();
    }
    
}
