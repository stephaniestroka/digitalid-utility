package net.digitalid.utility.freezable;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.rootclass.RootInterface;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * Interfaces that extend this interface provide read-only access to their objects.
 */
@ReadOnly(FreezableInterface.class)
public interface ReadOnlyInterface extends RootInterface, Cloneable {
    
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
     * Returns a non-frozen copy of this object of the corresponding {@link FreezableInterface freezable} type.
     */
    @Pure
    public @Capturable @Nonnull @NonFrozen FreezableInterface clone();
    
}
