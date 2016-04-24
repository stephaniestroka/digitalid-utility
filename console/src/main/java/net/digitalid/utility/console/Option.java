package net.digitalid.utility.console;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.console.exceptions.EscapeOptionException;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * Every option in the {@link Console console} has to extend this class.
 */
@Immutable
public abstract class Option {
    
    /* -------------------------------------------------- Description -------------------------------------------------- */
    
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
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new option with the given description.
     * 
     * @param description the description of the option.
     */
    protected Option(@Nonnull String description) {
        this.description = description;
    }
    
    /* -------------------------------------------------- Execution -------------------------------------------------- */
    
    /**
     * Executes this option.
     * 
     * @throws EscapeOptionException if the user escapes.
     */
    public abstract void execute() throws EscapeOptionException;
    
}
