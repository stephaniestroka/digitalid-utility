package net.digitalid.utility.contracts.exceptions;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Chainable;

@SuppressWarnings("null")
@Generated(value = "net.digitalid.utility.processor.generator.JavaFileGenerator")
public class PostconditionExceptionBuilder {
    
    public static class InnerPostconditionExceptionBuilder {
        
        private InnerPostconditionExceptionBuilder() {
        }
        
        /* -------------------------------------------------- Message -------------------------------------------------- */
        
        private String message = null;
        
        @Chainable
        public @Nonnull InnerPostconditionExceptionBuilder withMessage(String message) {
            this.message = message;
            return this;
        }
        
        /* -------------------------------------------------- Build -------------------------------------------------- */
        
        public PostconditionException build() {
            return new PostconditionExceptionSubclass(message);
        }
        
    }
    
    public static InnerPostconditionExceptionBuilder withMessage(String message) {
        return new InnerPostconditionExceptionBuilder().withMessage(message);
    }
    
}
