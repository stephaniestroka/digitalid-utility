package net.digitalid.utility.functional.fixes;

import net.digitalid.utility.annotations.method.Pure;

/**
 * Fixes surround a string with a prefix and a suffix.
 * 
 * @see Brackets
 * @see Comments
 * @see Quotes
 */
public interface Fixes {
    
    /* -------------------------------------------------- Prefix -------------------------------------------------- */
    
    /**
     * Returns the prefix.
     */
    @Pure
    public String getPrefix();
    
    /* -------------------------------------------------- Suffix -------------------------------------------------- */
    
    /**
     * Returns the suffix.
     */
    @Pure
    public String getSuffix();
    
    /* -------------------------------------------------- Both -------------------------------------------------- */
    
    /**
     * Returns the prefix directly followed by the suffix.
     */
    @Pure
    public default String getBoth() {
        return getPrefix() + getSuffix();
    }
    
}
