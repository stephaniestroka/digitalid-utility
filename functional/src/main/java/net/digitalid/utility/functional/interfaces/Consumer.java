package net.digitalid.utility.functional.interfaces;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Mutable;

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
    @Impure
    public void consume(@Captured T object);
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of this consumer followed by the given consumer.
     */
    @Pure
    public default @Capturable @Nonnull Consumer<T> before(@Captured @Nonnull Consumer<? super T> consumer) {
        return object -> { consume(object); consumer.consume(object); };
    }
    
    /**
     * Returns the composition of the given consumer followed by this consumer.
     */
    @Pure
    public default @Capturable @Nonnull Consumer<T> after(@Captured @Nonnull Consumer<? super T> consumer) {
        return object -> { consumer.consume(object); consume(object); };
    }
    
    /**
     * Returns the composition of the given function followed by this consumer.
     */
    @Pure
    public default <I> @Capturable @Nonnull Consumer<I> after(@Nonnull UnaryFunction<? super I, ? extends T> function) {
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
