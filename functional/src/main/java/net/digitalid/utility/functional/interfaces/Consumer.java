package net.digitalid.utility.functional.interfaces;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.failable.FailableConsumer;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This functional interface models a method that consumes objects of type {@code INPUT} without returning a result.
 */
@Mutable
@Functional
public interface Consumer<@Specifiable INPUT> extends FailableConsumer<INPUT, RuntimeException> {
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of this consumer followed by the given consumer.
     */
    @Pure
    public default @Capturable @Nonnull Consumer<INPUT> before(@Captured @Nonnull Consumer<? super INPUT> consumer) {
        return input -> { consume(input); consumer.consume(input); };
    }
    
    /**
     * Returns the composition of the given consumer followed by this consumer.
     */
    @Pure
    public default @Capturable @Nonnull Consumer<INPUT> after(@Captured @Nonnull Consumer<? super INPUT> consumer) {
        return input -> { consumer.consume(input); consume(input); };
    }
    
    /**
     * Returns the composition of the given function followed by this consumer.
     */
    @Pure
    public default @Capturable <@Specifiable INITIAL_INPUT> @Nonnull Consumer<INITIAL_INPUT> after(@Nonnull UnaryFunction<? super INITIAL_INPUT, ? extends INPUT> function) {
        return input -> consume(function.evaluate(input));
    }
    
    /* -------------------------------------------------- Conversion -------------------------------------------------- */
    
    @Pure
    @Override
    @SuppressWarnings("null")
    public default @Nonnull UnaryFunction<INPUT, @Nullable Void> asFunction() {
        return input -> { consume(input); return null; };
    }
    
    /* -------------------------------------------------- Synchronization -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull Consumer<INPUT> synchronize() {
        return input -> {
            synchronized (this) {
                consume(input);
            }
        };
    }
    
    /* -------------------------------------------------- Constants -------------------------------------------------- */
    
    /**
     * Stores a consumer that does nothing.
     */
    public static final @Nonnull Consumer<@Nullable Object> DO_NOTHING = input -> {};
    
}
