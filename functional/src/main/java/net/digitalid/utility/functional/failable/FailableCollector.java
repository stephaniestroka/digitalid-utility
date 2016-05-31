package net.digitalid.utility.functional.failable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.interfaces.Collector;
import net.digitalid.utility.functional.interfaces.Consumer;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * A failable collector consumes objects of type {@code T} and produces a result of type {@code R}.
 */
@Mutable
public interface FailableCollector<T, R, X extends Exception, Y extends Exception> extends FailableConsumer<T, X> {
    
    /* -------------------------------------------------- Result -------------------------------------------------- */
    
    /**
     * Returns the result of this collector.
     */
    @Pure
    public @Capturable R getResult() throws Y;
    
    /* -------------------------------------------------- Suppression -------------------------------------------------- */
    
    /**
     * Returns a collector that suppresses the exceptions of this collector, passes them to the given exception handler and returns the given default result instead.
     */
    @Pure
    public default @Capturable @Nonnull Collector<T, R> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler, @Captured R defaultResult) {
        return new Collector<T, R>() {
            
            @Impure
            @Override
            public void consume(@Captured T object) {
                try {
                    FailableCollector.this.consume(object);
                } catch (@Nonnull Exception exception) {
                    handler.consume(exception);
                }
            }
            
            @Pure
            @Override
            public @Capturable R getResult() {
                try {
                    return FailableCollector.this.getResult();
                } catch (@Nonnull Exception exception) {
                    handler.consume(exception);
                    return defaultResult;
                }
            }
            
        };
    }
    
    /**
     * Returns a collector that suppresses the exceptions of this collector, passes them to the given exception handler and returns null instead.
     */
    @Pure
    @Override
    public default @Capturable @Nonnull Collector<T, @Nullable R> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler) {
        return suppressExceptions(handler, null);
    }
    
    /**
     * Returns a collector that suppresses the exceptions of this collector and returns the given default result instead.
     */
    @Pure
    public default @Capturable @Nonnull Collector<T, R> suppressExceptions(@Captured R defaultResult) {
        return suppressExceptions(Consumer.DO_NOTHING, defaultResult);
    }
    
    /**
     * Returns a collector that suppresses the exceptions of this collector and returns null instead.
     */
    @Pure
    @Override
    public default @Capturable @Nonnull Collector<T, @Nullable R> suppressExceptions() {
        return suppressExceptions(Consumer.DO_NOTHING, null);
    }
    
    /* -------------------------------------------------- Synchronization -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull FailableCollector<T, R, X, Y> synchronize() {
        return new FailableCollector<T, R, X, Y>() {
            
            @Impure
            @Override
            public void consume(@Captured T object) throws X {
                synchronized (FailableCollector.this) {
                    FailableCollector.this.consume(object);
                }
            }
            
            @Pure
            @Override
            public @Capturable R getResult() throws Y {
                synchronized (FailableCollector.this) {
                    return FailableCollector.this.getResult();
                }
            }
            
        };
    }
    
}
