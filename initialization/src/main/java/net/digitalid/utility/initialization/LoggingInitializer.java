package net.digitalid.utility.initialization;

import javax.annotation.Nonnull;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.configuration.Initializer;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.string.QuoteString;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * This class adds logging capabilities to the {@link Initializer}.
 */
@Stateless
public abstract class LoggingInitializer extends Initializer {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates and registers this initializer with the given target and dependencies.
     */
    protected LoggingInitializer(@Nonnull Configuration<?> target, @Nonnull Configuration<?>... dependencies) {
        super(target, dependencies);
        
        Log.debugging("The initializer " + this + " was loaded.");
    }
    
    /* -------------------------------------------------- Execution -------------------------------------------------- */
    
    /**
     * Executes this initializer.
     * This method is only executed once.
     * 
     * @throws Exception if any problems occur.
     */
    protected abstract void executeWithoutLogging() throws Exception;
    
    @Override
    protected final void execute() throws Exception {
        Log.debugging("The initializer " + this + " begins its execution.");
        final long start = System.currentTimeMillis();
        executeWithoutLogging();
        final long end = System.currentTimeMillis();
        Log.debugging("The initializer " + this + " ends its execution in " + (end - start) + " ms.");
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return QuoteString.inSingle(getClass().getSimpleName());
    }
    
}
