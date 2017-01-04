package net.digitalid.utility.contracts.exceptions;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Chainable;

@SuppressWarnings("null")
@Generated(value = "net.digitalid.utility.processor.generator.JavaFileGenerator")
public class InvariantExceptionBuilder {
    
    public static class InnerInvariantExceptionBuilder {
        
        private InnerInvariantExceptionBuilder() {
        }
        
        /* -------------------------------------------------- Message -------------------------------------------------- */
        
        private String message = null;
        
        @Chainable
        public @Nonnull InnerInvariantExceptionBuilder withMessage(String message) {
            this.message = message;
            return this;
        }
        
        /* -------------------------------------------------- Build -------------------------------------------------- */
        
        public InvariantException build() {
            return new InvariantExceptionSubclass(message);
        }
        
    }
    
    public static InnerInvariantExceptionBuilder withMessage(String message) {
        return new InnerInvariantExceptionBuilder().withMessage(message);
    }
    
}
