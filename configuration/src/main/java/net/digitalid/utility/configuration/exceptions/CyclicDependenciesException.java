package net.digitalid.utility.configuration.exceptions;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.contracts.Require;

/**
 * This exception indicates that the configurations have cyclic dependencies.
 */
public class CyclicDependenciesException extends InitializationException {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    private final Configuration<?> configuration;
    
    /**
     * Returns the configuration to which the dependency should have been added.
     * 
     * @ensure result != null : "The returned configuration may not be null.";
     */
    public Configuration<?> getConfiguration() {
        return configuration;
    }
    
    /* -------------------------------------------------- Dependency -------------------------------------------------- */
    
    private final Configuration<?> dependency;
    
    /**
     * Returns the dependency which should have been added to the configuration.
     * 
     * @ensure result != null : "The returned configuration may not be null.";
     */
    public Configuration<?> getDependency() {
        return dependency;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    @SuppressWarnings("null")
    protected CyclicDependenciesException(Configuration<?> configuration, Configuration<?> dependency) {
        super("Could not add $ as a dependency of $, as $ already depends on $.", dependency, configuration, dependency, configuration, dependency.getDependencyChainAsString(configuration));
        
        Require.that(configuration != null).orThrow("The configuration may not be null.");
        Require.that(dependency != null).orThrow("The dependency may not be null.");
        
        this.configuration = configuration;
        this.dependency = dependency;
    }
    
    /**
     * Returns a cyclic dependencies exception with the given configuration and dependency.
     * 
     * @param configuration the configuration to which the dependency should have been added.
     * @param dependency the dependency which should have been added to the configuration.
     * 
     * @require configuration != null : "The configuration may not be null.";
     * @require dependency != null : "The dependency may not be null.";
     */
    public static CyclicDependenciesException with(Configuration<?> configuration, Configuration<?> dependency) {
        return new CyclicDependenciesException(configuration, dependency);
    }
    
}
