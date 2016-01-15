package net.digitalid.utility.taglets;

import java.util.Map;

/**
 * This class defines a custom block tag for constructor and method post-conditions.
 */
public final class Ensure extends Taglet {
    
    /* -------------------------------------------------- Registration -------------------------------------------------- */
    
    /**
     * Registers this taglet at the given map.
     * 
     * @param map the map at which this taglet is registered.
     */
    public static void register(Map<String, Taglet> map) {
        Taglet.register(map, new Ensure());
    }
    
    /* -------------------------------------------------- Overrides -------------------------------------------------- */
    
    @Override
    public boolean inConstructor() {
        return true;
    }
    
    @Override
    public boolean inMethod() {
        return true;
    }
    
    @Override
    public String getName() {
        return "ensure";
    }
    
    @Override
    public String getTitle() {
        return "Ensures";
    }
    
}
