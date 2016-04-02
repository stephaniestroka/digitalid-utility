package net.digitalid.utility.validation.interfaces;

import net.digitalid.utility.validation.annotations.math.NonNegative;

/**
 * Classes that have a size are countable.
 */
public interface Countable {
    
    /**
     * Returns the number of elements in this countable.
     */
    public @NonNegative int size();
    
}
