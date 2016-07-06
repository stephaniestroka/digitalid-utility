package net.digitalid.utility.functional.failable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.interfaces.Consumer;
import net.digitalid.utility.functional.interfaces.Producer;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This functional interface models a failable method that produces objects of type {@code T} without requiring a parameter.
 */
@Mutable
@Functional
public interface FailableProducer<T, X extends Exception> {
    
    /* -------------------------------------------------- Production -------------------------------------------------- */
    
    /**
     * Produces a result.
     */
    @Impure
    public @Capturable T produce() throws X;
    
    /* -------------------------------------------------- Suppression -------------------------------------------------- */
    
    /**
     * Returns a producer that catches the exceptions of this producer, passes them to the given exception handler and returns the given default output instead.
     */
    @Pure
    public default @Nonnull Producer<T> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler, @Captured T defaultOutput) {
        return () -> {
            try {
                return produce();
            } catch (@Nonnull Exception exception) {
                handler.consume(exception);
                return defaultOutput;
            }
        };
    }
    
    /**
     * Returns a producer that catches the exceptions of this producer, passes them to the given exception handler and returns null instead.
     */
    @Pure
    public default @Nonnull Producer<@Nullable T> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler) {
        return suppressExceptions(handler, null);
    }
    
    /**
     * Returns a producer that suppresses the exceptions of this producer and returns the given default output instead.
     */
    @Pure
    public default @Nonnull Producer<T> suppressExceptions(@Captured T defaultOutput) {
        return suppressExceptions(Consumer.DO_NOTHING, defaultOutput);
    }
    
    /**
     * Returns a producer that suppresses the exceptions of this producer and returns null instead.
     */
    @Pure
    public default @Nonnull Producer<@Nullable T> suppressExceptions() {
        return suppressExceptions(Consumer.DO_NOTHING, null);
    }
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of the given producer and function with a flexible exception type.
     */
    @Pure
    public static @Capturable <T, O, X extends Exception> @Nonnull FailableProducer<O, X> compose(@Captured @Nonnull FailableProducer<? extends T, ? extends X> producer, @Nonnull FailableUnaryFunction<? super T, ? extends O, ? extends X> function) {
        return () -> function.evaluate(producer.produce());
    }
    
    /**
     * Returns the composition of this producer followed by the given function.
     * Unfortunately, it is not possible to make the exception type flexible.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableProducer, net.digitalid.utility.functional.failable.FailableUnaryFunction)
     */
    @Pure
    public default <O> @Nonnull FailableProducer<O, X> before(@Nonnull FailableUnaryFunction<? super T, ? extends O, ? extends X> function) {
        return () -> function.evaluate(produce());
    }
    
    /* -------------------------------------------------- Conversion -------------------------------------------------- */
    
    /**
     * Returns this producer as a unary function that ignores its input.
     * This method may only be called if this producer is side-effect-free.
     */
    @Pure
    public default @Nonnull FailableUnaryFunction<@Nullable Object, T, X> asFunction() {
        return object -> produce();
    }
    
    /* -------------------------------------------------- Synchronization -------------------------------------------------- */
    
    /**
     * Returns a producer that synchronizes on this producer.
     */
    @Pure
    public default @Nonnull FailableProducer<T, X> synchronize() {
        return () -> {
            synchronized (this) {
                return produce();
            }
        };
    }
    
    /* -------------------------------------------------- Memoization -------------------------------------------------- */
    
    /**
     * Returns a producer that caches each object produced by this producer for the given duration in milliseconds.
     */
    @Pure
    public default @Nonnull FailableProducer<T, X> memoize(long duration) {
        return new FailableProducer<T, X>() {
            
            private T cachedObject = null;
            
            private long lastProduction = 0;
            
            @Impure
            @Override
            public T produce() throws X {
                final long currentTime = System.currentTimeMillis();
                if (lastProduction == 0 || lastProduction + duration < currentTime) {
                    this.cachedObject = FailableProducer.this.produce();
                    this.lastProduction = currentTime;
                }
                return cachedObject;
            }
            
        };
    }
    
}
