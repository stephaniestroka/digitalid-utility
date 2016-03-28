package net.digitalid.utility.functional.iterators;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * Description.
 */
public class FilterIterator {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected FilterIterator() {
        
    }
    
    @Pure
    public static @Nonnull FilterIterator with() {
        return new FilterIterator();
    }
    
}
