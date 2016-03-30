package net.digitalid.utility.functional.fixes;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class enumerates various comments.
 */
@Immutable
public enum Comments implements Fixes {
    
    /* -------------------------------------------------- Enumerations -------------------------------------------------- */
    
    /**
     * The Java comment prefix '/* ' and suffix ' *\/'.
     */
    JAVA("/* ", " */"),
    
    /**
     * The XML comment prefix '<!-- ' and suffix ' -->'.
     */
    XML("<!-- ", " -->");
    
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
    
    private Comments(@Nonnull String prefix, @Nonnull String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }
    
}
