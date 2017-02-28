package net.digitalid.utility.console;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.console.exceptions.EscapeException;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * Every option in the {@link Console console} has to extend this class.
 */
@Immutable
public abstract class Option /* extends RootClass */ {
    
    /* -------------------------------------------------- Description -------------------------------------------------- */
    
    private final @Nonnull String description;
    
    /**
     * Returns the description of this option.
     */
    @Pure
    public @Nonnull String getDescription() {
        return description;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    protected Option(@Nonnull String description) {
        this.description = description;
    }
    
    /* -------------------------------------------------- Execution -------------------------------------------------- */
    
    /**
     * Executes this option.
     * 
     * @throws EscapeException if the user escapes.
     */
    @Pure
    public abstract void execute() throws EscapeException;
    
}
