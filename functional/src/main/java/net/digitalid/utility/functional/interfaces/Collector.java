package net.digitalid.utility.functional.interfaces;

import net.digitalid.utility.annotations.method.Pure;

/**
 * A collector consumes objects of type {@code T} and produces a result of type {@code R}.
 */
public interface Collector<T, R> extends Consumer<T> {
    
    /**
     * Returns the result of this collector.
     */
    @Pure
    public R getResult();
    
}
