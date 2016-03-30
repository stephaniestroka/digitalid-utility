package net.digitalid.utility.casting;

import javax.annotation.Nonnull;

import net.digitalid.utility.casting.exceptions.InvalidClassCastException;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This class implements an easy way to cast an object to a subclass.
 */
public abstract class CastableClass implements Castable {
    
    /* -------------------------------------------------- Casting -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull <T> T castTo(@Nonnull Class<T> targetClass) throws InvalidClassCastException {
        if (targetClass.isInstance(this)) { return targetClass.cast(this); }
        else { throw InvalidClassCastException.get(this, targetClass); }
    }
    
}
