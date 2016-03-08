package net.digitalid.utility.tuples;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * Description.
 */
public class NonNullableQuartet {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NonNullableQuartet() {
        
    }
    
    @Pure
    public static @Nonnull NonNullableQuartet with() {
        return new NonNullableQuartet();
    }
    
}
