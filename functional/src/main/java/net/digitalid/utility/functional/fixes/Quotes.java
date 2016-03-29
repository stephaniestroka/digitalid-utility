package net.digitalid.utility.functional.fixes;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This class enumerates various quotation marks.
 */
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
    
    private Quotes(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }
    
}
