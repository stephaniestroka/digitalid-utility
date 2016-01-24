package net.digitalid.utility.configuration;

import java.util.LinkedHashSet;
import java.util.ServiceLoader;
import java.util.Set;

/**
 * The configuration of a service is given by a provider.
 * A provider can only be replaced but no longer removed.
 * 
 * @param <P> the type of the provider for some service.
 */
public class Configuration<P> {
    
    /* -------------------------------------------------- Observers -------------------------------------------------- */
    
    /**
     * Stores the non-nullable set of registered observers of this configuration.
     */
    private final Set<ConfigurationObserver<P>> observers = new LinkedHashSet<>();
    
    /**
     * Registers the given non-nullable observer for this configuration.
     * 
     * @require observer != null : "The given observer is not null.";
     */
    public void register(ConfigurationObserver<P> observer) {
        assert observer != null : "The given observer is not null.";
        
        observers.add(observer);
    }
    
    /**
     * Deregisters the given non-nullable observer for this configuration.
     * 
     * @require observer != null : "The given observer is not null.";
     */
    public void deregister(ConfigurationObserver<P> observer) {
        assert observer != null : "The given observer is not null.";
        
        observers.remove(observer);
    }
    
    /**
     * Returns whether the given non-nullable observer is registered for this configuration.
     * 
     * @require observer != null : "The given observer is not null.";
     */
    public boolean isRegistered(ConfigurationObserver<P> observer) {
        assert observer != null : "The given observer is not null.";
        
        return observers.contains(observer);
    }
    
    /* -------------------------------------------------- Provider -------------------------------------------------- */
    
    /**
     * Stores the provider of this configuration.
     * The provider is null until it is once set.
     */
    private P provider;
    
    /**
     * Returns the provider of this configuration.
     * 
     * @ensure result != null : "The returned provider is not null";
     * 
     * @throws InitializationError if no provider was set for this configuration
     */
    public P get() {
        if (provider == null) {
            throw InitializationError.of("No provider was set for this configuration.");
        }
        return provider;
    }
    
    /**
     * Sets the provider of this configuration and notifies all observers.
     * 
     * @require provider != null : "The provider is not null.";
     */
    public void set(P provider) {
        assert provider != null : "The provider is not null.";
        
        if (!provider.equals(this.provider)) {
            for (ConfigurationObserver<P> observer : observers) {
                observer.replaced(this, this.provider, provider);
            }
            this.provider = provider;
        }
    }
    
    /* -------------------------------------------------- Configurations -------------------------------------------------- */
    
    /**
     * Stores the non-nullable set of all configurations that were created.
     */
    private static final Set<Configuration<?>> configurations = new LinkedHashSet<>();
    
    /**
     * Initializes all configurations of this library.
     * 
     * @throws InitializationError if an issue occurs.
     */
    public static void initializeAllConfigurations() {
        try {
            for (Initializer initializer : ServiceLoader.load(Initializer.class)) {
                System.out.println(initializer.getClass() + " was loaded."); // TODO: Remove the output.
            }
            for (Configuration<?> configuration : configurations) {
                configuration.initialize();
            }
        } catch (Throwable throwable) {
            throw InitializationError.of("A problem occurred during the initialization of the library.", throwable);
        }
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a configuration with the given nullable provider.
     */
    protected Configuration(P provider) {
        this.provider = provider;
        configurations.add(this);
    }
    
    /**
     * Returns a configuration with the given non-nullable provider.
     * 
     * @require provider != null : "The given provider is not null.";
     */
    public static <P> Configuration<P> of(P provider) {
        assert provider != null : "The given provider is not null.";
        
        return new Configuration<>(provider);
    }
    
    /**
     * Returns a configuration whose provider still needs to be set.
     */
    public static <P> Configuration<P> ofUnknownProvider() {
        return new Configuration<>(null);
    }
    
    /* -------------------------------------------------- Initializers -------------------------------------------------- */
    
    /**
     * Stores the non-nullable set of initializers which need to be executed by this configuration.
     */
    private final Set<Initializer> initializers = new LinkedHashSet<>();
    
    /**
     * Adds the given non-nullable initializer to the set of initializers for this configuration.
     * 
     * @throws InitializationError if this configuration is already initialized.
     * 
     * @require initializer != null : "The given initializer is not null.";
     */
    protected void add(Initializer initializer) {
        assert initializer != null : "The given initializer is not null.";
        
        if (isInitialized) {
            throw InitializationError.of("This configuration is already initialized.");
        }
        
        initializers.add(initializer);
    }
    
    /* -------------------------------------------------- Dependencies -------------------------------------------------- */
    
    /**
     * Stores the non-nullable set of configurations which need to be initialized before this configuration.
     */
    private final Set<Configuration<?>> dependencies = new LinkedHashSet<>();
    
    /**
     * Returns whether this configuration depends on the given non-nullable configuration (directly or indirectly).
     */
    protected boolean dependsOn(Configuration<?> configuration) {
        if (configuration.equals(this)) { return true; }
        for (Configuration<?> dependency : dependencies) {
            if (dependency.dependsOn(configuration)) { return true; }
        }
        return false;
    }
    
    /**
     * Adds the given non-nullable dependency to the set of dependencies for this configuration.
     * 
     * @throws InitializationError if the given dependency depends on this configuration.
     * @throws InitializationError if this configuration is already initialized.
     * 
     * @require dependency != null : "The given dependency is not null.";
     */
    protected void add(Configuration<?> dependency) {
        assert dependency != null : "The given dependency is not null.";
        
        if (isInitialized) {
            throw InitializationError.of("This configuration is already initialized.");
        }
        
        if (dependency.dependsOn(this)) {
            throw InitializationError.of("The given dependency depends on this configuration.");
        }
        
        dependencies.add(dependency);
    }
    
    /* -------------------------------------------------- Initialization -------------------------------------------------- */
    
    /**
     * Stores whether this configuration has been initialized.
     */
    private boolean isInitialized = false;
    
    /**
     * Initializes all dependencies and executes all initializers of this configuration.
     * 
     * @throws Throwable if any problems occur.
     */
    protected void initialize() throws Throwable {
        if (!isInitialized) {
            for (Configuration<?> dependency : dependencies) {
                dependency.initialize();
            }
            for (Initializer initializer : initializers) {
                initializer.execute();
            }
            this.isInitialized = true;
        }
    }
    
}
