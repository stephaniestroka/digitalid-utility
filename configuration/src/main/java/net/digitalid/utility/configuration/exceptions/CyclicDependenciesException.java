package net.digitalid.utility.configuration.exceptions;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception indicates that the configurations have cyclic dependencies.
 */
@Immutable
public class CyclicDependenciesException extends InitializationException {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    private final @Nonnull Configuration<?> configuration;
    
    /**
     * Returns the configuration to which the dependency should have been added.
     */
    @Pure
    public @Nonnull Configuration<?> getConfiguration() {
        return configuration;
    }
    
    /* -------------------------------------------------- Dependency -------------------------------------------------- */
    
    private final @Nonnull Configuration<?> dependency;
    
    /**
     * Returns the dependency which should have been added to the configuration.
     */
    @Pure
    public @Nonnull Configuration<?> getDependency() {
        return dependency;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    @SuppressWarnings("null")
    protected CyclicDependenciesException(@Nonnull Configuration<?> configuration, @Nonnull Configuration<?> dependency) {
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
     */
    @Pure
    public static @Nonnull CyclicDependenciesException with(@Nonnull Configuration<?> configuration, @Nonnull Configuration<?> dependency) {
        return new CyclicDependenciesException(configuration, dependency);
    }
    
}
