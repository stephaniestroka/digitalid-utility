package net.digitalid.utility.functional.interfaces;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This functional interface models a method that consumes an object without returning a result.
 */
public interface Consumer<T> {
    
    /**
     * Consumes the given object.
     */
    public void consume(T object);
    
    /**
     * Returns the composition of this consumer followed by the given consumer.
     */
    @Pure
    public default Consumer<T> before(Consumer<? super T> consumer) {
        return object -> { consume(object); consumer.consume(object); };
    }
    
    /**
     * Returns the composition of the given consumer followed by this consumer.
     */
    @Pure
    public default Consumer<T> after(Consumer<? super T> consumer) {
        return object -> { consumer.consume(object); consume(object); };
    }
    
    /**
     * Returns the composition of the given function followed by this consumer.
     */
    @Pure
    public default <I> Consumer<I> after(UnaryFunction<? super I, ? extends T> function) {
        return object -> consume(function.evaluate(object));
    }
    
}
