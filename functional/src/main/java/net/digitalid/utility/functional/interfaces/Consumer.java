package net.digitalid.utility.functional.interfaces;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.type.Functional;

/**
 * This functional interface models a method that consumes objects of type {@code T} without returning a result.
 */
@Mutable
@Functional
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
    public default @Nonnull Consumer<T> before(@Nonnull Consumer<? super T> consumer) {
        return object -> { consume(object); consumer.consume(object); };
    }
    
    /**
     * Returns the composition of the given consumer followed by this consumer.
     */
    @Pure
    public default @Nonnull Consumer<T> after(@Nonnull Consumer<? super T> consumer) {
        return object -> { consumer.consume(object); consume(object); };
    }
    
    /**
     * Returns the composition of the given function followed by this consumer.
     */
    @Pure
    public default <I> @Nonnull Consumer<I> after(@Nonnull UnaryFunction<? super I, ? extends T> function) {
        return object -> consume(function.evaluate(object));
    }
    
    /* -------------------------------------------------- Conversion -------------------------------------------------- */
    
    /**
     * Returns this consumer as a unary function that always returns null.
     * This method may only be called if this consumer is side-effect-free.
     */
    @Pure
    public default @Nonnull UnaryFunction<T, @Nullable Void> asFunction() {
        return object -> { consume(object); return null; };
    }
    
}
