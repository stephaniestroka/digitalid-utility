package net.digitalid.utility.functional.interfaces;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This functional interface models a method that produces an object without requiring a parameter.
 */
public interface Producer<T> {
    
    /**
     * Produces a result.
     */
    public T produce();
    
    /**
     * Returns the composition of this producer followed by the given function.
     */
    @Pure
    public default <O> Producer<O> after(UnaryFunction<? super T, ? extends O> function) {
        return () -> function.evaluate(produce());
    }
    
}
