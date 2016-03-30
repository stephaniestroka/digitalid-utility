package net.digitalid.utility.functional.interfaces;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.type.Functional;

/**
 * This functional interface models a method that produces objects of type {@code T} without requiring a parameter.
 */
@Mutable
@Functional
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
    public default <O> @Nonnull Producer<O> after(@Nonnull UnaryFunction<? super T, ? extends O> function) {
        return () -> function.evaluate(produce());
    }
    
    /* -------------------------------------------------- Conversion -------------------------------------------------- */
    
    /**
     * Returns this producer as a unary function that ignores its input.
     * This method may only be called if this producer is side-effect-free.
     */
    @Pure
    public default @Nonnull UnaryFunction<@Nullable Object, T> asFunction() {
        return object -> produce();
    }
    
}
