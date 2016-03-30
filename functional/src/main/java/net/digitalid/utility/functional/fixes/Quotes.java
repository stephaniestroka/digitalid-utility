package net.digitalid.utility.functional.fixes;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class enumerates various quotation marks.
 */
@Immutable
public enum Quotes implements Fixes {
    
    /* -------------------------------------------------- Enumerations -------------------------------------------------- */
    
    /**
     * The single quotes '\'' and '\''.
     */
    SINGLE("'", "'"),
    
    /**
     * The double quotes '"' and '"'.
     */
    DOUBLE("\"", "\""),
    
    /**
     * The angle quotes '«' and '»'.
     */
    ANGLE("«", "»"),
    
    /**
     * The code quotes '"' and '"'.
     * The intention is to use the quotes
     * only in case the object is a string.
     */
    CODE("\"", "\"");
    
    /* -------------------------------------------------- Prefix -------------------------------------------------- */
    
    private final @Nonnull String prefix;
    
    @Pure
    @Override
    public @Nonnull String getPrefix() {
        return prefix;
    }
    
    /* -------------------------------------------------- Suffix -------------------------------------------------- */
    
    private final @Nonnull String suffix;
    
    @Pure
    @Override
    public @Nonnull String getSuffix() {
        return suffix;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    private Quotes(@Nonnull String prefix, @Nonnull String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }
    
}
