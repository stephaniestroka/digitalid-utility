package net.digitalid.utility.configuration;

/**
 * Objects that implement this interface can be used to observe a {@link Configuration configuration}.
 */
public interface ConfigurationObserver<P> {
    
    /**
     * This method is called on {@link Configuration#register(ConfigurationObserver) registered} observers when the provider of the given configuration has been replaced.
     * 
     * @param configuration the non-nullable configuration whose provider has been replaced.
     * @param oldProvider the nullable old provider of the given configuration.
     * @param newProvider the non-nullable new provider of the given configuration.
     * 
     * @require !newProvider.equals(oldProvider) : "The new provider is not the same as the old provider.";
     */
    public void replaced(Configuration<P> configuration, P oldProvider, P newProvider);
    
}
