package net.digitalid.utility.conversion.exceptions;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
        
        /* -------------------------------------------------- Cause -------------------------------------------------- */
        
        private @Nullable Throwable cause = null;
        
        @Chainable
        public @Nonnull InnerRecoveryExceptionBuilder withCause(@Nullable Throwable cause) {
            this.cause = cause;
            return this;
        }
        
        /* -------------------------------------------------- Build -------------------------------------------------- */
        
        public RecoveryException build() {
            return new RecoveryExceptionSubclass(message, cause);
        }
        
    }
    
    public static InnerRecoveryExceptionBuilder withMessage(String message) {
        return new InnerRecoveryExceptionBuilder().withMessage(message);
    }
    
}
