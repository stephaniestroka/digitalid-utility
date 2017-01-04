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
 * This functional interface models a method that consumes objects of type {@code T} without returning a result.
 */
@Mutable
@Functional
public interface Consumer<@Specifiable TYPE> extends FailableConsumer<TYPE, RuntimeException> {
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of this consumer followed by the given consumer.
     */
    @Pure
    public default @Capturable @Nonnull Consumer<TYPE> before(@Captured @Nonnull Consumer<? super TYPE> consumer) {
        return object -> { consume(object); consumer.consume(object); };
    }
    
    /**
     * Returns the composition of the given consumer followed by this consumer.
     */
    @Pure
    public default @Capturable @Nonnull Consumer<TYPE> after(@Captured @Nonnull Consumer<? super TYPE> consumer) {
        return object -> { consumer.consume(object); consume(object); };
    }
    
    /**
     * Returns the composition of the given function followed by this consumer.
     */
    @Pure
    public default @Capturable <@Specifiable INPUT> @Nonnull Consumer<INPUT> after(@Nonnull UnaryFunction<? super INPUT, ? extends TYPE> function) {
        return object -> consume(function.evaluate(object));
    }
    
    /* -------------------------------------------------- Conversion -------------------------------------------------- */
    
    @Pure
    @Override
    @SuppressWarnings("null")
    public default @Nonnull UnaryFunction<TYPE, @Nullable Void> asFunction() {
        return object -> { consume(object); return null; };
    }
    
    /* -------------------------------------------------- Synchronization -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull Consumer<TYPE> synchronize() {
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
