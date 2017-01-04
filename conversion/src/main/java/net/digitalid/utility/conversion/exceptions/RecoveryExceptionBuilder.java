package net.digitalid.utility.conversion.exceptions;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Chainable;

@SuppressWarnings("null")
@Generated(value = "net.digitalid.utility.processor.generator.JavaFileGenerator")
public class RecoveryExceptionBuilder {
    
    public static class InnerRecoveryExceptionBuilder {
        
        private InnerRecoveryExceptionBuilder() {
        }
        
        /* -------------------------------------------------- Message -------------------------------------------------- */
        
        private String message = null;
        
        @Chainable
        public @Nonnull InnerRecoveryExceptionBuilder withMessage(String message) {
            this.message = message;
            return this;
        }
        
        /* -------------------------------------------------- Build -------------------------------------------------- */
        
        public RecoveryException build() {
            return new RecoveryExceptionSubclass(message);
        }
        
    }
    
    public static InnerRecoveryExceptionBuilder withMessage(String message) {
        return new InnerRecoveryExceptionBuilder().withMessage(message);
    }
    
}
