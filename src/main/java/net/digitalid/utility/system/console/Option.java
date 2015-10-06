package net.digitalid.utility.system.console;

import javax.annotation.Nonnull;
import net.digitalid.utility.annotations.state.Immutable;
import net.digitalid.utility.annotations.state.Pure;
import net.digitalid.utility.system.console.exceptions.EscapeOptionException;

/**
 * Every option in the {@link Console console} has to extend this class.
 * 
 * @author Kaspar Etter (kaspar.etter@digitalid.net)
 * @version 1.0.0
 */
@Immutable
public abstract class Option {
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Description –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    /**
     * Stores the description of the option.
     */
    private final @Nonnull String description;
    
    /**
     * Returns the description of this option.
     * 
     * @return the description of this option.
     */
    @Pure
    public final @Nonnull String getDescription() {
        return description;
    }
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Constructor –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    /**
     * Creates a new option with the given description.
     * 
     * @param description the description of the option.
     */
    protected Option(@Nonnull String description) {
        this.description = description;
    }
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Execution –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    /**
     * Executes this option.
     * 
     * @throws EscapeOptionException if the user escapes.
     */
    public abstract void execute() throws EscapeOptionException;
    
}
