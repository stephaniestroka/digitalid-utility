package net.digitalid.utility.functional.failable;



import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.interfaces.Consumer;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This functional interface models a failable method that consumes objects of type {@code T} without returning a result.
 */
@Mutable
@Functional
public interface FailableConsumer<T, X extends Exception> {
    
    /* -------------------------------------------------- Consumption -------------------------------------------------- */
    
    /**
     * Consumes the given object.
     */
    @Impure
    public void consume(@Captured T object) throws X;
    
    /* -------------------------------------------------- Suppression -------------------------------------------------- */
    
    /**
     * Returns a consumer that suppresses the exceptions of this consumer and passes them to the given exception handler instead.
     */
    @Pure
    public default @Capturable @Nonnull Consumer<T> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler) {
        return object -> {
            try {
                consume(object);
            } catch (@Nonnull Exception exception) {
                handler.consume(exception);
            }
        };
    }
    
    /**
     * Returns a consumer that suppresses the exceptions of this consumer.
     */
    @Pure
    public default @Capturable @Nonnull Consumer<T> suppressExceptions() {
        return suppressExceptions(Consumer.DO_NOTHING);
    }
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of the given consumers with a flexible exception type.
     */
    @Pure
    public static @Capturable <T, X extends Exception> @Nonnull FailableConsumer<T, X> compose(@Captured @Nonnull FailableConsumer<? super T, ? extends X> consumer0, @Captured @Nonnull FailableConsumer<? super T, ? extends X> consumer1) {
        return object -> { consumer0.consume(object); consumer1.consume(object); };
    }
    
    /**
     * Returns the composition of this consumer followed by the given consumer.
     * Unfortunately, it is not possible to make the exception type flexible as well.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableConsumer, net.digitalid.utility.functional.failable.FailableConsumer)
     */
    @Pure
    public default @Capturable <S extends T> @Nonnull FailableConsumer<S, X> before(@Captured @Nonnull FailableConsumer<? super S, ? extends X> consumer) {
        return object -> { consume(object); consumer.consume(object); };
    }
    
    /**
     * Returns the composition of the given consumer followed by this consumer.
     * Unfortunately, it is not possible to make the exception type flexible as well.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableConsumer, net.digitalid.utility.functional.failable.FailableConsumer)
     */
    @Pure
    public default @Capturable <S extends T> @Nonnull FailableConsumer<S, X> after(@Captured @Nonnull FailableConsumer<? super S, ? extends X> consumer) {
        return object -> { consumer.consume(object); consume(object); };
    }
    
    /**
     * Returns the composition of the given function followed by this consumer.
     * Unfortunately, it is not possible to make the exception type flexible as well.
     */
    @Pure
    public default @Capturable <I> @Nonnull FailableConsumer<I, X> after(@Nonnull FailableUnaryFunction<? super I, ? extends T, ? extends X> function) {
        return object -> consume(function.evaluate(object));
    }
    
    /* -------------------------------------------------- Conversion -------------------------------------------------- */
    
    /**
     * Returns this consumer as a unary function that always returns null.
     * This method may only be called if this consumer is side-effect-free.
     */
    @Pure
    public default @Nonnull FailableUnaryFunction<T, @Nullable Void, X> asFunction() {
        return object -> { consume(object); return null; };
    }
    
    /* -------------------------------------------------- Synchronization -------------------------------------------------- */
    
    /**
     * Returns a consumer that synchronizes on this consumer.
     */
    @Pure
    public default @Nonnull FailableConsumer<T, X> synchronize() {
        return object -> {
            synchronized (this) {
                consume(object);
            }
        };
    }
    
}
