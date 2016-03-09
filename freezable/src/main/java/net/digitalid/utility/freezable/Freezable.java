package net.digitalid.utility.freezable;

import javax.annotation.Nonnull;

import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * Classes that implement this interface allow their objects to transition from a mutable into an {@link Immutable immutable} state.
 */
public interface Freezable extends ReadOnly {
    
    /**
     * Freezes this object and thus makes it immutable.
     * 
     * @ensure isFrozen() : "This object is now frozen.";
     */
    public @Chainable @Nonnull @Frozen ReadOnly freeze();
    
}
