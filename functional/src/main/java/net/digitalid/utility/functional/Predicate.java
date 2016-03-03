package net.digitalid.utility.functional;

import javax.annotation.Nullable;

/**
 * Description.
 */
public interface Predicate<T> {
    
    public boolean apply(@Nullable T object);
    
}
