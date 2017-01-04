package net.digitalid.utility.configuration.errors;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.configuration.Initializer;
import net.digitalid.utility.contracts.Ensure;

@SuppressWarnings("null")
@Generated(value = "net.digitalid.utility.processor.generator.JavaFileGenerator")
class InitializerErrorSubclass extends InitializerError {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    private final @Nonnull Configuration<?> configuration;
    
    @Override
    public @Nonnull Configuration<?> getConfiguration() {
        @Nonnull Configuration<?> result = this.configuration;
        Ensure.that(result != null).orThrow("The result may not be null.", result);
        Ensure.that(result != null).orThrow("The result may not be null.", result);
        return result;
    }
    
    /* -------------------------------------------------- Initializer -------------------------------------------------- */
    
    private final @Nonnull Initializer initializer;
    
    @Override
    public @Nonnull Initializer getInitializer() {
        @Nonnull Initializer result = this.initializer;
        Ensure.that(result != null).orThrow("The result may not be null.", result);
        Ensure.that(result != null).orThrow("The result may not be null.", result);
        return result;
    }
    
    /* -------------------------------------------------- Cause -------------------------------------------------- */
    
    private final @Nonnull Throwable cause;
    
    @Override
    public @Nonnull Throwable getCause() {
        @Nonnull Throwable result = this.cause;
        Ensure.that(result != null).orThrow("The result may not be null.", result);
        Ensure.that(result != null).orThrow("The result may not be null.", result);
        return result;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    InitializerErrorSubclass(@Nonnull Configuration<?> configuration, @Nonnull Initializer initializer, @Nonnull Throwable cause) {
        super();
        
        this.configuration = configuration;
        this.initializer = initializer;
        this.cause = cause;
    }
    
    /* -------------------------------------------------- Overridden Methods -------------------------------------------------- */
    
    @Override
    public String getSummary() {
        String result = super.getSummary();
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
