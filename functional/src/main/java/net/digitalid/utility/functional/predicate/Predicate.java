package net.digitalid.utility.functional.predicate;

import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 *
 */
@Stateless
public interface Predicate<T> {
    
    /**
     * Applies the predicate on a given object and returns the boolean result.
     */
    @Pure
    public abstract boolean apply(T object);
    
}
