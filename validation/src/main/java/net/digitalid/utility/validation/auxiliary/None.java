package net.digitalid.utility.validation.auxiliary;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * This class is an alternative to {@link Void} which supports non-nullable parameters and return values.
 */
@Stateless
public final class None {
    
    /**
     * Creates a new none.
     */
    private None() {}
    
    /**
     * Stores the only object of this class.
     */
    public static final @Nonnull None OBJECT = new None();
    
}
