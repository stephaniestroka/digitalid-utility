package net.digitalid.utility.immutable.entry;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.type.Mutable;

/**
 * Description.
 */
@Mutable
public class ReadOnlyEntrySetIterator {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ReadOnlyEntrySetIterator() {
        
    }
    
    @Pure
    public static @Nonnull ReadOnlyEntrySetIterator with() {
        return new ReadOnlyEntrySetIterator();
    }
    
}
