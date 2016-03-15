package net.digitalid.utility.functional.predicate;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * This interface defines a function that can be used to check whether an input satisfies a specific predicate.
 */
@Stateless
public interface Predicate<T> {
    
    /**
     * Applies the predicate on a given object and returns the boolean result.
     */
    @Pure
    public abstract boolean apply(T object);
    
}
