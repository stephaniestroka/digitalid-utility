package net.digitalid.utility.casting;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This interface provides an easy way to cast an object to a subclass.
 */
@Mutable
public interface Castable {
    
    /* -------------------------------------------------- Casting -------------------------------------------------- */
    
    /**
     * Casts this object to the given target class.
     * 
     * @require targetClass.isInstance(this) : "This object is an instance of the given target class.";
     */
    @Pure
    public default @Chainable <T> @Nonnull T castTo(@Nonnull Class<T> targetClass) {
        Require.that(targetClass.isInstance(this)).orThrow("This object $ has to be an instance of the target class $.", this, targetClass);
        
        return targetClass.cast(this);
    }
    
}
