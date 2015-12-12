package net.digitalid.utility.system.castable;

import javax.annotation.Nonnull;
import net.digitalid.utility.annotations.state.Pure;
import net.digitalid.utility.system.exceptions.external.InvalidClassCastException;

/**
 * This interface provides an easy way to cast an object to a subclass.
 */
public interface Castable {
    
    /* -------------------------------------------------- Casting -------------------------------------------------- */
    
    /**
     * Casts this object to the given target class, if possible.
     * 
     * @param targetClass the class to which the object should be casted.
     * 
     * @return this object casted to the given target class.
     * 
     * @throws InvalidClassCastException if this object cannot be cast to the given target class.
     * 
     * @ensure return == this : "This object is returned.";
     */
    @Pure
    public @Nonnull <T> T castTo(@Nonnull Class<T> targetClass) throws InvalidClassCastException;
    
}
