package net.digitalid.utility.taglets;

import java.util.Map;

/**
 * This class defines a custom block tag for constructor and method preconditions.
 */
public final class Require extends Taglet {
    
    /* -------------------------------------------------- Registration -------------------------------------------------- */
    
    /**
     * Registers this taglet at the given map.
     * 
     * @param map the map at which this taglet is registered.
     */
    public static void register(Map<String, Taglet> map) {
        Taglet.register(map, new Require());
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
        return "require";
    }
    
    @Override
    public String getTitle() {
        return "Requires";
    }
    
}
