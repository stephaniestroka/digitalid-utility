package net.digitalid.utility.configuration;

/**
 * An initializer initializes a certain configuration (its target).
 * It can require that other configurations are initialized before.
 */
public abstract class Initializer {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates and registers an initializer with the given non-nullable target and non-nullable dependencies.
     */
    protected Initializer(Configuration<?> target, Configuration<?>... dependencies) {
        target.addInitializer(this);
        for (Configuration<?> dependency : dependencies) {
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
