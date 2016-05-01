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
    
}
