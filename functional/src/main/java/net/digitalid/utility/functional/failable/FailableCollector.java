package net.digitalid.utility.functional.failable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.interfaces.Collector;
import net.digitalid.utility.functional.interfaces.Consumer;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * A failable collector consumes objects of type {@code INPUT} and produces a result of type {@code RESULT}.
 */
@Mutable
public interface FailableCollector<@Specifiable INPUT, @Specifiable RESULT, @Unspecifiable COLLECT_EXCEPTION extends Exception, @Unspecifiable RESULT_EXCEPTION extends Exception> extends FailableConsumer<INPUT, COLLECT_EXCEPTION> {
    
    /* -------------------------------------------------- Result -------------------------------------------------- */
    
    /**
     * Returns the result of this collector.
     */
    @Pure
    public @Capturable RESULT getResult() throws RESULT_EXCEPTION;
    
    /* -------------------------------------------------- Suppression -------------------------------------------------- */
    
    /**
     * Returns a collector that suppresses the exceptions of this collector, passes them to the given exception handler and returns the given default result instead.
     */
    @Pure
    public default @Capturable @Nonnull Collector<INPUT, RESULT> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler, @Captured RESULT defaultResult) {
        return new Collector<INPUT, RESULT>() {
            
            @Impure
            @Override
            public void consume(@Captured INPUT input) {
                try {
                    FailableCollector.this.consume(input);
                } catch (@Nonnull Exception exception) {
                    handler.consume(exception);
                }
            }
            
            @Pure
            @Override
            public @Capturable RESULT getResult() {
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
    public default @Capturable @Nonnull Collector<INPUT, @Nullable RESULT> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler) {
        return suppressExceptions(handler, null);
    }
    
    /**
     * Returns a collector that suppresses the exceptions of this collector and returns the given default result instead.
     */
    @Pure
    public default @Capturable @Nonnull Collector<INPUT, RESULT> suppressExceptions(@Captured RESULT defaultResult) {
        return suppressExceptions(Consumer.DO_NOTHING, defaultResult);
    }
    
    /**
     * Returns a collector that suppresses the exceptions of this collector and returns null instead.
     */
    @Pure
    @Override
    public default @Capturable @Nonnull Collector<INPUT, @Nullable RESULT> suppressExceptions() {
        return suppressExceptions(Consumer.DO_NOTHING, null);
    }
    
    /* -------------------------------------------------- Synchronization -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull FailableCollector<INPUT, RESULT, COLLECT_EXCEPTION, RESULT_EXCEPTION> synchronize() {
        return new FailableCollector<INPUT, RESULT, COLLECT_EXCEPTION, RESULT_EXCEPTION>() {
            
            @Impure
            @Override
            public void consume(@Captured INPUT input) throws COLLECT_EXCEPTION {
                synchronized (FailableCollector.this) {
                    FailableCollector.this.consume(input);
                }
            }
            
            @Pure
            @Override
            public @Capturable RESULT getResult() throws RESULT_EXCEPTION {
                synchronized (FailableCollector.this) {
                    return FailableCollector.this.getResult();
                }
            }
            
        };
    }
    
}
