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
 * A collector consumes objects of type {@code INPUT} and produces a result of type {@code RESULT}.
 */
@Mutable
public interface Collector<@Specifiable INPUT, @Specifiable RESULT> extends Consumer<INPUT>, FailableCollector<INPUT, RESULT, RuntimeException, RuntimeException> {
    
    /* -------------------------------------------------- Synchronization -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull Collector<INPUT, RESULT> synchronize() {
        return new Collector<INPUT, RESULT>() {
            
            @Impure
            @Override
            public void consume(@Captured INPUT input) {
                synchronized (Collector.this) {
                    Collector.this.consume(input);
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
