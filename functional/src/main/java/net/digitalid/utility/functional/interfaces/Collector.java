package net.digitalid.utility.functional.interfaces;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.type.Mutable;

/**
 * A collector consumes objects of type {@code T} and produces a result of type {@code R}.
 */
@Mutable
public interface Collector<T, R> extends Consumer<T> {
    
    /**
     * Returns the result of this collector.
     */
    @Pure
    public @Capturable R getResult();
    
}
