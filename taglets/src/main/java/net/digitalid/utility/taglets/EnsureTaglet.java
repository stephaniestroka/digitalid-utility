package net.digitalid.utility.taglets;

import java.util.Map;

import com.sun.tools.doclets.Taglet;

/**
 * This class defines a custom block tag for constructor and method postconditions.
 */
public final class EnsureTaglet extends CustomTaglet {
    
    /* -------------------------------------------------- Registration -------------------------------------------------- */
    
    /**
     * Registers this taglet at the given non-nullable map of registered taglets.
     */
    public static void register(Map<String, Taglet> registeredTaglets) {
        CustomTaglet.register(registeredTaglets, new EnsureTaglet());
    }
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    @Override
    public boolean inConstructor() {
        return true;
    }
    
    @Override
    public boolean inMethod() {
        return true;
    }
    
    /* -------------------------------------------------- Content -------------------------------------------------- */
    
    @Override
    public String getName() {
        return "ensure";
    }
    
    @Override
    public String getTitle() {
        return "Ensures";
    }
    
}
