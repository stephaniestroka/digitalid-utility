package net.digitalid.utility.taglets;

import java.util.Map;

/**
 * This class defines a custom block tag for class (and field) invariants.
 */
public final class Invariant extends Taglet {
    
    /* -------------------------------------------------- Registration -------------------------------------------------- */
    
    /**
     * Registers this taglet at the given map.
     * 
     * @param map the map at which this taglet is registered.
     */
    public static void register(Map<String, Taglet> map) {
        Taglet.register(map, new Invariant());
    }
    
    /* -------------------------------------------------- Overrides -------------------------------------------------- */
    
    @Override
    public boolean inField() {
        return true;
    }
    
    @Override
    public boolean inType() {
        return true;
    }
    
    @Override
    public String getName() {
        return "invariant";
    }
    
    @Override
    public String getTitle() {
        return "Invariant";
    }
    
}
