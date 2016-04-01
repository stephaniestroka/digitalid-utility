package net.digitalid.utility.functional.fixes;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class enumerates various brackets.
 */
@Immutable
public enum Brackets implements Fixes {
    
    /* -------------------------------------------------- Enumerations -------------------------------------------------- */
    
    /**
     * The round brackets '(' and ')'.
     */
    ROUND("(", ")"),
    
    /**
     * The square brackets '[' and ']'.
     */
    SQUARE("[", "]"),
    
    /**
     * The curly brackets '{' and '}'.
     */
    CURLY("{", "}"),
    
    /**
     * The pointy brackets '<' and '>'.
     */
    POINTY("<", ">"),
    
    /**
     * The curly brackets with a space after the opening and before the closing bracket.
     */
    JSON("{ ", " }");
    
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
    
    private Brackets(@Nonnull String prefix, @Nonnull String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }
    
}
