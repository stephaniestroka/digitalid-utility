package net.digitalid.utility.collections.freezable;

import javax.annotation.Nonnull;
import net.digitalid.utility.annotations.state.Immutable;
import net.digitalid.utility.collections.annotations.freezable.Frozen;
import net.digitalid.utility.collections.readonly.ReadOnly;

/**
 * Classes that implement this interface allow their objects to transition from a mutable into an {@link Immutable immutable} state.
 * 
 * @see FreezableIterable
 * @see FreezableIterator
 * @see FreezableObject
 * @see FreezableMap
 * 
 * @author Kaspar Etter (kaspar.etter@digitalid.net)
 * @version 1.0.0
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
