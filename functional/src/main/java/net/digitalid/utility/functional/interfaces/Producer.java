package net.digitalid.utility.functional.interfaces;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.failable.FailableProducer;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This functional interface models a method that produces objects of type {@code T} without requiring a parameter.
 */
@Mutable
@Functional
public interface Producer<T> extends FailableProducer<T, RuntimeException> {
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of this producer followed by the given function.
     */
    @Pure
    public default <O> @Nonnull Producer<O> before(@Nonnull UnaryFunction<? super T, ? extends O> function) {
        return () -> function.evaluate(produce());
    }
    
    /* -------------------------------------------------- Conversion -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull UnaryFunction<@Nullable Object, T> asFunction() {
        return object -> produce();
    }
    
    /* -------------------------------------------------- Synchronization -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull Producer<T> synchronize() {
        return () -> {
            synchronized (this) {
                return produce();
            }
        };
    }
    
    /* -------------------------------------------------- Memoization -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull Producer<T> memoize(long duration) {
        return new Producer<T>() {
            
            private T cachedObject = null;
            
            private long lastProduction = 0;
            
            @Impure
            @Override
            public T produce() {
                final long currentTime = System.currentTimeMillis();
                if (lastProduction == 0 || lastProduction + duration < currentTime) {
                    this.cachedObject = Producer.this.produce();
                    this.lastProduction = currentTime;
                }
                return cachedObject;
            }
            
        };
    }
    
    /* -------------------------------------------------- Constant -------------------------------------------------- */
    
    /**
     * Returns a producer that always produces the given object.
     */
    @Pure
    public static <T> @Nonnull Producer<T> constant(@Captured T object) {
        return () -> object;
    }
    
}
