package net.digitalid.utility.functional.fixes;

import net.digitalid.utility.annotations.method.Pure;

/**
 * This class enumerates various comments.
 */
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
    
    private Comments(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }
    
}
