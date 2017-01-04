package net.digitalid.utility.configuration.errors;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.validation.annotations.method.Chainable;

@SuppressWarnings("null")
@Generated(value = "net.digitalid.utility.processor.generator.JavaFileGenerator")
public class DependencyErrorBuilder {
    
    public interface DependencyDependencyErrorBuilder {
        
        @Chainable
        public @Nonnull InnerDependencyErrorBuilder withDependency(@Nonnull Configuration<?> dependency);
        
    }
    
    public static class InnerDependencyErrorBuilder implements DependencyDependencyErrorBuilder {
        
        private InnerDependencyErrorBuilder() {
        }
        
        /* -------------------------------------------------- Configuration -------------------------------------------------- */
        
        private @Nonnull Configuration<?> configuration = null;
        
        @Chainable
        public @Nonnull InnerDependencyErrorBuilder withConfiguration(@Nonnull Configuration<?> configuration) {
            this.configuration = configuration;
            return this;
        }
        
        /* -------------------------------------------------- Dependency -------------------------------------------------- */
        
        private @Nonnull Configuration<?> dependency = null;
        
        @Chainable
        public @Nonnull InnerDependencyErrorBuilder withDependency(@Nonnull Configuration<?> dependency) {
            this.dependency = dependency;
            return this;
        }
        
        /* -------------------------------------------------- Build -------------------------------------------------- */
        
        public DependencyError build() {
            return new DependencyErrorSubclass(configuration, dependency);
        }
        
    }
    
    public static DependencyDependencyErrorBuilder withConfiguration(@Nonnull Configuration<?> configuration) {
        return new InnerDependencyErrorBuilder().withConfiguration(configuration);
    }
    
}
