package net.digitalid.utility.contracts.exceptions;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Chainable;

@SuppressWarnings("null")
@Generated(value = "net.digitalid.utility.processor.generator.JavaFileGenerator")
public class PreconditionExceptionBuilder {
    
    public static class InnerPreconditionExceptionBuilder {
        
        private InnerPreconditionExceptionBuilder() {
        }
        
        /* -------------------------------------------------- Message -------------------------------------------------- */
        
        private String message = null;
        
        @Chainable
        public @Nonnull InnerPreconditionExceptionBuilder withMessage(String message) {
            this.message = message;
            return this;
        }
        
        /* -------------------------------------------------- Build -------------------------------------------------- */
        
        public PreconditionException build() {
            return new PreconditionExceptionSubclass(message);
        }
        
    }
    
    public static InnerPreconditionExceptionBuilder withMessage(String message) {
        return new InnerPreconditionExceptionBuilder().withMessage(message);
    }
    
}
