package net.digitalid.utility.casting;

import javax.annotation.Nonnull;

import net.digitalid.utility.casting.exceptions.InvalidClassCastException;
import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This interface provides an easy way to cast an object to a subclass.
 */
public interface Castable {
    
    /* -------------------------------------------------- Casting -------------------------------------------------- */
    
    /**
     * Casts this object to the given target class, if possible.
     * 
     * @throws InvalidClassCastException if this object cannot be cast to the given target class.
     */
    @Pure
    public @Chainable @Nonnull <T> T castTo(@Nonnull Class<T> targetClass) throws InvalidClassCastException;
    
}
