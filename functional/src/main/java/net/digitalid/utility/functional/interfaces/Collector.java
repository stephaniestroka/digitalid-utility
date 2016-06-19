package net.digitalid.utility.functional.interfaces;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.failable.FailableCollector;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * A collector consumes objects of type {@code T} and produces a result of type {@code R}.
 */
@Mutable
public interface Collector<T, R> extends Consumer<T>, FailableCollector<T, R, RuntimeException, RuntimeException> {
    
    /* -------------------------------------------------- Synchronization -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull Collector<T, R> synchronize() {
        return new Collector<T, R>() {
            
            @Impure
            @Override
            public void consume(@Captured T object) {
                synchronized (Collector.this) {
                    Collector.this.consume(object);
                }
            }
            
            @Pure
            @Override
            public @Capturable R getResult() {
                synchronized (Collector.this) {
                    return Collector.this.getResult();
                }
            }
            
        };
    }
    
}
