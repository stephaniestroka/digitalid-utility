package net.digitalid.utility.functional.fixes;

import net.digitalid.utility.annotations.method.Pure;

/**
 * This class enumerates various brackets.
 */
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
    POINTY("<", ">");
    
    /* -------------------------------------------------- Prefix -------------------------------------------------- */
    
    private final String prefix;
    
    @Pure
    @Override
    public String getPrefix() {
        return prefix;
    }
    
    /* -------------------------------------------------- Suffix -------------------------------------------------- */
    
    private final String suffix;
    
    @Pure
    @Override
    public String getSuffix() {
        return suffix;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    private Brackets(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }
    
}
