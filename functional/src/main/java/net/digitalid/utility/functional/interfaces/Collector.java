package net.digitalid.utility.functional.interfaces;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.failable.FailableCollector;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * A collector consumes objects of type {@code TYPE} and produces a result of type {@code RESULT}.
 */
@Mutable
public interface Collector<@Specifiable TYPE, @Specifiable RESULT> extends Consumer<TYPE>, FailableCollector<TYPE, RESULT, RuntimeException, RuntimeException> {
    
    /* -------------------------------------------------- Synchronization -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull Collector<TYPE, RESULT> synchronize() {
        return new Collector<TYPE, RESULT>() {
            
            @Impure
            @Override
            public void consume(@Captured TYPE object) {
                synchronized (Collector.this) {
                    Collector.this.consume(object);
                }
            }
            
            @Pure
            @Override
            public @Capturable RESULT getResult() {
                synchronized (Collector.this) {
                    return Collector.this.getResult();
                }
            }
            
        };
    }
    
}
