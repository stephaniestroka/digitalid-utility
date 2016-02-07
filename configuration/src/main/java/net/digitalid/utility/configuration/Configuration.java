package net.digitalid.utility.configuration;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.digitalid.utility.configuration.exceptions.CyclicDependenciesException;
import net.digitalid.utility.configuration.exceptions.InitializedConfigurationException;
import net.digitalid.utility.configuration.exceptions.UninitializedConfigurationException;
import net.digitalid.utility.contracts.Require;

/**
 * The configuration of a service is given by a provider.
 * A provider can only be replaced but no longer removed.
 * 
 * @param <P> the type of the provider for some service.
 */
public class Configuration<P> {
    
    /* -------------------------------------------------- Interface -------------------------------------------------- */
    
    /**
     * Objects that implement this interface can be used to observe a {@link Configuration configuration}.
     */
    public static interface Observer<P> {
        
        /**
         * This method is called on {@link Configuration#register(net.digitalid.utility.configuration.Configuration.Observer) registered} observers when the provider of the given configuration has been replaced.
         * 
         * @param configuration the non-nullable configuration whose provider has been replaced.
         * @param oldProvider the nullable old provider of the given configuration.
         * @param newProvider the non-nullable new provider of the given configuration.
         * 
         * @require !newProvider.equals(oldProvider) : "The new provider may not the same as the old provider.";
         */
        public void replaced(Configuration<P> configuration, P oldProvider, P newProvider);
        
    }
    
    /* -------------------------------------------------- Observers -------------------------------------------------- */
    
    /**
     * Stores the non-nullable set of registered observers of this configuration.
     */
    private final Set<Observer<P>> observers = new LinkedHashSet<>();
    
    /**
     * Registers the given non-nullable observer for this configuration.
     * 
     * @return whether the given observer was not already registered.
     * 
     * @require observer != null : "The observer may not be null.";
     */
    public boolean register(Observer<P> observer) {
        Require.that(observer != null).orThrow("The observer may not be null.");
        
        return observers.add(observer);
    }
    
    /**
     * Deregisters the given non-nullable observer for this configuration.
     * 
     * @return whether the given observer was actually registered.
     * 
     * @require observer != null : "The observer may not be null.";
     */
    public boolean deregister(Observer<P> observer) {
        Require.that(observer != null).orThrow("The observer may not be null.");
        
        return observers.remove(observer);
    }
    
    /**
     * Returns whether the given non-nullable observer is registered for this configuration.
     * 
     * @require observer != null : "The observer may not be null.";
     */
    public boolean isRegistered(Observer<P> observer) {
        Require.that(observer != null).orThrow("The observer may not be null.");
        
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
     * @ensure result != null : "The returned provider may not be null.";
     * 
     * @throws UninitializedConfigurationException if no provider was set for this configuration.
     */
    public P get() {
        if (provider == null) { throw UninitializedConfigurationException.with(this); }
        return provider;
    }
    
    /**
     * Sets the provider of this configuration and notifies all observers.
     * 
     * @require provider != null : "The provider may not be null.";
     */
    public void set(P provider) {
        Require.that(provider != null).orThrow("The provider may not be null.");
        
        if (!provider.equals(this.provider)) {
            for (Observer<P> observer : observers) {
                observer.replaced(this, this.provider, provider);
            }
            this.provider = provider;
        }
    }
    
    /**
     * Returns whether the provider of this configuration is set.
     */
    public boolean isSet() {
        return provider != null;
    }
    
    /* -------------------------------------------------- Declaration -------------------------------------------------- */
    
    private final String qualifiedClassName;
    
    /**
     * Returns the non-nullable qualified name of the class where this configuration is declared.
     */
    public String getQualifiedClassName() {
        return qualifiedClassName;
    }
    
    private final String className;
    
    /**
     * Returns the non-nullable simple name of the class where this configuration is declared.
     */
    public String getClassName() {
        return className;
    }
    
    private final int lineNumber;
    
    /**
     * Returns the line number on which this configuration is declared.
     */
    public int getLineNumber() {
        return lineNumber;
    }
    
    @Override
    public String toString() {
        return getClassName();
    }
    
    /* -------------------------------------------------- Configurations -------------------------------------------------- */
    
    /**
     * Stores the non-nullable set of all configurations that were created.
     */
    private static final Set<Configuration<?>> configurations = new LinkedHashSet<>();
    
    /**
     * Returns an unmodifiable set of all configurations that were created.
     * 
     * @ensure result != null : "The returned set may not be null.";
     */
    public static Set<Configuration<?>> getAllConfigurations() {
        return Collections.unmodifiableSet(configurations);
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a configuration with the given nullable provider.
     */
    protected Configuration(P provider) {
        this.provider = provider;
        
        final StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        this.qualifiedClassName = element.getClassName();
        this.className = qualifiedClassName.substring(qualifiedClassName.lastIndexOf('.') + 1);
        this.lineNumber = element.getLineNumber();
        
        configurations.add(this);
    }
    
    /**
     * Returns a configuration with the given non-nullable provider.
     * 
     * @require provider != null : "The provider may not be null.";
     */
    public static <P> Configuration<P> with(P provider) {
        Require.that(provider != null).orThrow("The provider may not be null.");
        
        return new Configuration<>(provider);
    }
    
    /**
     * Returns a configuration whose provider still needs to be set.
     */
    public static <P> Configuration<P> withUnknownProvider() {
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
     * @throws InitializedConfigurationException if this configuration has already been initialized.
     * 
     * @require initializer != null : "The initializer may not be null.";
     */
    protected void addInitializer(Initializer initializer) {
        Require.that(initializer != null).orThrow("The initializer may not be null.");
        
        if (isInitialized) { throw InitializedConfigurationException.with(this); }
        initializers.add(initializer);
    }
    
    /* -------------------------------------------------- Dependencies -------------------------------------------------- */
    
    /**
     * Stores the non-nullable set of configurations which need to be initialized before this configuration.
     */
    private final Set<Configuration<?>> dependencies = new LinkedHashSet<>();
    
    /**
     * Returns whether this configuration depends on the given non-nullable configuration (directly or indirectly).
     * 
     * @require configuration != null : "The configuration may not be null.";
     */
    public boolean dependsOn(Configuration<?> configuration) {
        Require.that(configuration != null).orThrow("The configuration may not be null.");
        
        if (configuration.equals(this)) { return true; }
        for (Configuration<?> dependency : dependencies) {
            if (dependency.dependsOn(configuration)) { return true; }
        }
        return false;
    }
    
    private static final List<Configuration<?>> EMPTY_LIST = Collections.unmodifiableList(new LinkedList<Configuration<?>>());
    
    /**
     * Returns a non-nullable list of configurations through which this configuration depends on the given configuration.
     * The returned list is empty if (and only if) this configuration does not depend on the given configuration.
     * Otherwise, the first element in the returned list is this configuration and the last the given configuration.
     * If (and only if) the returned list is not empty, it is modifiable and can be captured by the caller.
     * 
     * @require configuration != null : "The configuration may not be null.";
     */
    public List<Configuration<?>> getDependencyChain(Configuration<?> configuration) {
        Require.that(configuration != null).orThrow("The configuration may not be null.");
        
        if (configuration.equals(this)) {
            final LinkedList<Configuration<?>> result = new LinkedList<>();
            result.add(this);
            return result;
        }
        
        for (Configuration<?> dependency : dependencies) {
            final List<Configuration<?>> dependencyChain = dependency.getDependencyChain(configuration);
            if (!dependencyChain.isEmpty()) {
                dependencyChain.add(0, this);
                return dependencyChain;
            }
        }
        
        return EMPTY_LIST;
    }
    
    /**
     * Returns the {@link #getDependencyChain(net.digitalid.utility.configuration.Configuration) dependency chain} as a non-nullable string.
     * 
     * @require configuration != null : "The configuration may not be null.";
     * @require dependsOn(configuration) : "This configuration has to depend on the given configuration.";
     */
    public String getDependencyChainAsString(Configuration<?> configuration) {
        Require.that(configuration != null).orThrow("The configuration may not be null.");
        Require.that(dependsOn(configuration)).orThrow("This configuration has to depend on the given configuration.");
        
        final StringBuilder result = new StringBuilder();
        final List<Configuration<?>> dependencyChain = getDependencyChain(configuration);
        for (Configuration<?> dependency : dependencyChain) {
            if (result.length() == 0) { result.append(" -> "); }
            result.append(dependency);
        }
        return result.toString();
    }
    
    /**
     * Adds the given non-nullable dependency to the set of dependencies and returns this configuration.
     * 
     * @throws InitializedConfigurationException if this configuration has already been initialized.
     * @throws CyclicDependenciesException if the given dependency depends on this configuration.
     * 
     * @require dependency != null : "The dependency may not be null.";
     * @ensure result == this : "The returned configuration is this.";
     */
    public Configuration<P> addDependency(Configuration<?> dependency) {
        Require.that(dependency != null).orThrow("The dependency may not be null.");
        
        if (isInitialized) { throw InitializedConfigurationException.with(this); }
        if (dependency.dependsOn(this)) { throw CyclicDependenciesException.with(this, dependency); }
        dependencies.add(dependency);
        return this;
    }
    
    /* -------------------------------------------------- Initialization -------------------------------------------------- */
    
    /**
     * Stores whether this configuration has been initialized.
     */
    private boolean isInitialized = false;
    
    /**
     * Initializes all dependencies and executes all initializers of this configuration.
     * 
     * @throws Exception if any problems occur.
     */
    public void initialize() throws Exception {
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
