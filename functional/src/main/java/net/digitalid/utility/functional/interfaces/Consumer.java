package net.digitalid.utility.functional.interfaces;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This functional interface models a method that consumes objects of type {@code T} without returning a result.
 */
public interface Consumer<T> {
    
    /* -------------------------------------------------- Consumption -------------------------------------------------- */
    
    /**
     * Consumes the given object.
     */
    public void consume(T object);
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
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
    
    /* -------------------------------------------------- Conversion -------------------------------------------------- */
    
    /**
     * Returns this consumer as a unary function that always returns null.
     * This method may only be called if this consumer is side-effect-free.
     */
    @Pure
    public default UnaryFunction<T, Void> asFunction() {
        return object -> { consume(object); return null; };
    }
    
}
