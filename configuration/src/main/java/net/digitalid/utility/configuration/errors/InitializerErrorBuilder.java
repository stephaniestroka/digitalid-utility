package net.digitalid.utility.configuration.errors;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.configuration.Initializer;
import net.digitalid.utility.validation.annotations.method.Chainable;

@SuppressWarnings("null")
@Generated(value = "net.digitalid.utility.processor.generator.JavaFileGenerator")
public class InitializerErrorBuilder {
    
    public interface InitializerInitializerErrorBuilder {
        
        @Chainable
        public @Nonnull CauseInitializerErrorBuilder withInitializer(@Nonnull Initializer initializer);
        
    }
    
    public interface CauseInitializerErrorBuilder {
        
        @Chainable
        public @Nonnull InnerInitializerErrorBuilder withCause(@Nonnull Throwable cause);
        
    }
    
    public static class InnerInitializerErrorBuilder implements InitializerInitializerErrorBuilder, CauseInitializerErrorBuilder {
        
        private InnerInitializerErrorBuilder() {
        }
        
        /* -------------------------------------------------- Configuration -------------------------------------------------- */
        
        private @Nonnull Configuration<?> configuration = null;
        
        @Chainable
        public @Nonnull InnerInitializerErrorBuilder withConfiguration(@Nonnull Configuration<?> configuration) {
            this.configuration = configuration;
            return this;
        }
        
        /* -------------------------------------------------- Initializer -------------------------------------------------- */
        
        private @Nonnull Initializer initializer = null;
        
        @Chainable
        public @Nonnull InnerInitializerErrorBuilder withInitializer(@Nonnull Initializer initializer) {
            this.initializer = initializer;
            return this;
        }
        
        /* -------------------------------------------------- Cause -------------------------------------------------- */
        
        private @Nonnull Throwable cause = null;
        
        @Chainable
        public @Nonnull InnerInitializerErrorBuilder withCause(@Nonnull Throwable cause) {
            this.cause = cause;
            return this;
        }
        
        /* -------------------------------------------------- Build -------------------------------------------------- */
        
        public InitializerError build() {
            return new InitializerErrorSubclass(configuration, initializer, cause);
        }
        
    }
    
    public static InitializerInitializerErrorBuilder withConfiguration(@Nonnull Configuration<?> configuration) {
        return new InnerInitializerErrorBuilder().withConfiguration(configuration);
    }
    
}
