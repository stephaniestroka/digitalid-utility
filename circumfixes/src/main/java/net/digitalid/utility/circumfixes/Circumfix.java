package net.digitalid.utility.circumfixes;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A circumfix surrounds a string with a prefix and a suffix.
 * 
 * @see Brackets
 * @see Comments
 * @see Quotes
 */
@Immutable
public interface Circumfix {
    
    /* -------------------------------------------------- Prefix -------------------------------------------------- */
    
    /**
     * Returns the prefix.
     */
    @Pure
    public @Nonnull String getPrefix();
    
    /* -------------------------------------------------- Suffix -------------------------------------------------- */
    
    /**
     * Returns the suffix.
     */
    @Pure
    public @Nonnull String getSuffix();
    
    /* -------------------------------------------------- Both -------------------------------------------------- */
    
    /**
     * Returns the prefix directly followed by the suffix.
     */
    @Pure
    public default @Nonnull String getBoth() {
        return getPrefix() + getSuffix();
    }
    
}
