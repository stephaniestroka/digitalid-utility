package net.digitalid.utility.taglets;

import java.util.Map;
import javax.annotation.Nonnull;
import net.digitalid.utility.annotations.state.Pure;
import net.digitalid.utility.annotations.state.Stateless;

/**
 * This class defines a custom block tag for constructor and method preconditions.
 */
@Stateless
public final class Require extends Taglet {
    
    /* -------------------------------------------------- Registration -------------------------------------------------- */
    
    /**
     * Registers this taglet at the given map.
     * 
     * @param map the map at which this taglet is registered.
     */
    public static void register(@Nonnull Map<String, Taglet> map) {
        Taglet.register(map, new Require());
    }
    
    /* -------------------------------------------------- Overrides -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean inConstructor() {
        return true;
    }
    
    @Pure
    @Override
    public boolean inMethod() {
        return true;
    }
    
    @Pure
    @Override
    public @Nonnull String getName() {
        return "require";
    }
    
    @Pure
    @Override
    public @Nonnull String getTitle() {
        return "Requires";
    }
    
}
