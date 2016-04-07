package net.digitalid.utility.immutable.entry;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * 
 */
@ReadOnly
public class ReadOnlyEntry {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ReadOnlyEntry() {
        
    }
    
    public static @Nonnull ReadOnlyEntry with() {
        return new ReadOnlyEntry();
    }
    
}
