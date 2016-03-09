package net.digitalid.utility.tuples;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * Description.
 */
public class NonNullableTriplet {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NonNullableTriplet() {
        
    }
    
    @Pure
    public static @Nonnull NonNullableTriplet with() {
        return new NonNullableTriplet();
    }
    
}
