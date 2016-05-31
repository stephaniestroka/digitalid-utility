package net.digitalid.utility.functional.interfaces;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.failable.FailableConsumer;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This functional interface models a method that consumes objects of type {@code T} without returning a result.
 */
@Mutable
@Functional
public interface Consumer<T> extends FailableConsumer<T, RuntimeException> {
    
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
    
    @Pure
    @Override
    public default @Nonnull UnaryFunction<T, @Nullable Void> asFunction() {
        return object -> { consume(object); return null; };
    }
    
    /* -------------------------------------------------- Synchronization -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull Consumer<T> synchronize() {
        return object -> {
            synchronized (this) {
                consume(object);
            }
        };
    }
    
    /* -------------------------------------------------- Constants -------------------------------------------------- */
    
    /**
     * Stores a consumer that does nothing.
     */
    public static final @Nonnull Consumer<@Nullable Object> DO_NOTHING = object -> {};
    
}
