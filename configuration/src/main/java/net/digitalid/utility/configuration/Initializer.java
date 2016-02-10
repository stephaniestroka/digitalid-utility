package net.digitalid.utility.configuration;

import net.digitalid.utility.contracts.Require;

/**
 * An initializer initializes a certain configuration (its target).
 * It can require that other configurations are initialized before.
 */
public abstract class Initializer {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates and registers this initializer with the given non-nullable target and non-nullable dependencies.
     */
    protected Initializer(Configuration<?> target, Configuration<?>... dependencies) {
        Require.that(target != null).orThrow("The target may not be null.");
        Require.that(dependencies != null).orThrow("The dependencies may not be null.");
        
        target.addInitializer(this);
        for (Configuration<?> dependency : dependencies) {
            Require.that(dependency != null).orThrow("Each dependency may not be null.");
            target.addDependency(dependency);
        }
    }
    
    /* -------------------------------------------------- Execution -------------------------------------------------- */
    
    /**
     * Executes this initializer.
     * This method is only executed once.
     * 
     * @throws Exception if any problems occur.
     */
    protected abstract void execute() throws Exception;
    
}
