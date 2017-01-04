package net.digitalid.utility.configuration.errors;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.contracts.Ensure;

@SuppressWarnings("null")
@Generated(value = "net.digitalid.utility.processor.generator.JavaFileGenerator")
class ConfigurationErrorSubclass extends ConfigurationError {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    private final @Nonnull Configuration<?> configuration;
    
    @Override
    public @Nonnull Configuration<?> getConfiguration() {
        @Nonnull Configuration<?> result = this.configuration;
        Ensure.that(result != null).orThrow("The result may not be null.", result);
        Ensure.that(result != null).orThrow("The result may not be null.", result);
        return result;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    ConfigurationErrorSubclass(@Nonnull Configuration<?> configuration) {
        super();
        
        this.configuration = configuration;
    }
    
    /* -------------------------------------------------- Overridden Methods -------------------------------------------------- */
    
    @Override
    public String getSummary() {
        String result = super.getSummary();
        return result;
    }
    
    @Override
    public Throwable getCause() {
        Throwable result = super.getCause();
        return result;
    }
    
    @Override
    public @Nonnull String getMessage() {
        @Nonnull String result = super.getMessage();
        Ensure.that(result != null).orThrow("The result may not be null.", result);
        Ensure.that(result != null).orThrow("The result may not be null.", result);
        return result;
    }
    
    /* -------------------------------------------------- Implement Methods -------------------------------------------------- */
    
    /* -------------------------------------------------- Generated Methods -------------------------------------------------- */
    
}
