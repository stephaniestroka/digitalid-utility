package net.digitalid.utility.configuration.errors;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.validation.annotations.method.Chainable;

@SuppressWarnings("null")
@Generated(value = "net.digitalid.utility.processor.generator.JavaFileGenerator")
public class ConfigurationErrorBuilder {
    
    public static class InnerConfigurationErrorBuilder {
        
        private InnerConfigurationErrorBuilder() {
        }
        
        /* -------------------------------------------------- Configuration -------------------------------------------------- */
        
        private @Nonnull Configuration<?> configuration = null;
        
        @Chainable
        public @Nonnull InnerConfigurationErrorBuilder withConfiguration(@Nonnull Configuration<?> configuration) {
            this.configuration = configuration;
            return this;
        }
        
        /* -------------------------------------------------- Build -------------------------------------------------- */
        
        public ConfigurationError build() {
            return new ConfigurationErrorSubclass(configuration);
        }
        
    }
    
    public static InnerConfigurationErrorBuilder withConfiguration(@Nonnull Configuration<?> configuration) {
        return new InnerConfigurationErrorBuilder().withConfiguration(configuration);
    }
    
}
