package net.digitalid.utility.freezable;

import javax.annotation.Nonnull;

import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.readonly.ReadOnly;
import net.digitalid.utility.validation.state.Immutable;

/**
 * Classes that implement this interface allow their objects to transition from a mutable into an {@link Immutable immutable} state.
 * 
 * @see FreezableObject
 */
public interface Freezable extends ReadOnly {
    
    /**
     * Freezes this object and thus makes it immutable.
     * Make sure to overwrite this method and freeze all mutable fields!
     * 
     * @return a reference to this object in order to allow chaining.
     * 
     * @ensure isFrozen() : "This object is now frozen.";
     */
    public @Nonnull @Frozen ReadOnly freeze();
    
}
