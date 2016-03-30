package net.digitalid.utility.freezable;

import javax.annotation.Nonnull;

import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;

/**
 * Interfaces that extend this interface provide read-only access to their objects.
 * 
 * @see Freezable
 */
public interface ReadOnly extends Cloneable {
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    /**
     * Returns whether this object is frozen and can thus no longer be modified.
     * 
     * @ensure !old(isFrozen()) || isFrozen() : "Once frozen, this object remains frozen.";
     */
    @Pure
    public boolean isFrozen();
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    /**
     * Returns a non-frozen copy of this object of the corresponding {@link Freezable freezable} type.
     */
    @Pure
    public @Capturable @Nonnull @NonFrozen Freezable clone();
    
}
