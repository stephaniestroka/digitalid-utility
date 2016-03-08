package net.digitalid.utility.tuples;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * Description.
 */
public class NonNullablePair {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NonNullablePair() {
        
    }
    
    @Pure
    public static @Nonnull NonNullablePair with() {
        return new NonNullablePair();
    }
    
}
