package net.digitalid.utility.taglets;

import java.util.Map;

import com.sun.tools.doclets.Taglet;

/**
 * This class defines a custom block tag for class and field invariants.
 */
public class Invariant extends CustomTaglet {
    
    /* -------------------------------------------------- Registration -------------------------------------------------- */
    
    /**
     * Registers this taglet at the given non-nullable map of registered taglets.
     */
    public static void register(Map<String, Taglet> registeredTaglets) {
        CustomTaglet.register(registeredTaglets, new Invariant());
    }
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    @Override
    public boolean inField() {
        return true;
    }
    
    @Override
    public boolean inType() {
        return true;
    }
    
    /* -------------------------------------------------- Content -------------------------------------------------- */
    
    @Override
    public String getName() {
        return "invariant";
    }
    
    @Override
    public String getTitle() {
        return "Invariant";
    }
    
}
