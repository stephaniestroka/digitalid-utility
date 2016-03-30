package net.digitalid.utility.generator;

import javax.annotation.Nonnull;

import net.digitalid.utility.freezable.Freezable;
import net.digitalid.utility.freezable.ReadOnly;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.tuples.annotations.Pure;
import net.digitalid.utility.validation.annotations.reference.Capturable;

/**
 * This class implements the freezing mechanism which can be reused with inheritance.
 */
@Deprecated // TODO: Generate this implementation instead.
public class FreezableObject implements Freezable {
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    /**
     * Stores whether this object is frozen.
     */
    private boolean frozen = false;
    
    @Pure
    @Override
    public final boolean isFrozen() {
        return frozen;
    }
    
    @Override
    public @Nonnull @Frozen ReadOnly freeze() {
        frozen = true;
        return this;
    }
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableObject clone() {
        return new FreezableObject();
    }
    
}
