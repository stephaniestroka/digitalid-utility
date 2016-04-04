package net.digitalid.utility.casting;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.casting.exceptions.InvalidClassCastException;
import net.digitalid.utility.validation.annotations.method.Chainable;

/**
 * This class implements an easy way to cast an object to a subclass.
 */
@Mutable
public abstract class CastableClass implements Castable {
    
    /* -------------------------------------------------- Casting -------------------------------------------------- */
    
    @Pure
    @Override
    public <T> @Chainable @Nonnull T castTo(@Nonnull Class<T> targetClass) throws InvalidClassCastException {
        if (targetClass.isInstance(this)) { return targetClass.cast(this); }
        else { throw InvalidClassCastException.get(this, targetClass); }
    }
    
}
