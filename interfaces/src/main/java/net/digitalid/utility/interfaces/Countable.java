package net.digitalid.utility.interfaces;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.Functional;

/**
 * Classes that have a size are countable.
 */
@Functional
public interface Countable {
    
    /**
     * Returns the number of elements in this countable.
     */
    @Pure
    public @NonNegative int size();
    
    /**
     * Returns whether this countable is empty.
     */
    @Pure
    public default boolean isEmpty() {
        return size() == 0;
    }
    
    /**
     * Returns whether this countable is single.
     */
    @Pure
    public default boolean isSingle() {
        return size() == 1;
    }
    
    /**
     * Returns whether this countable is empty or single.
     */
    @Pure
    public default boolean isEmptyOrSingle() {
        return isEmpty() || isSingle();
    }
    
}
