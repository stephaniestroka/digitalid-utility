package net.digitalid.utility.functional.exceptions;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.type.Utility;

@Utility
@SuppressWarnings("null")
public class IterationExceptionBuilder {
    
    public static class InnerIterationExceptionBuilder {
        
        private InnerIterationExceptionBuilder() {
        }
        
        /* -------------------------------------------------- Cause -------------------------------------------------- */
        
        private Exception cause = null;
        
        @Chainable
        public @Nonnull InnerIterationExceptionBuilder withCause(Exception cause) {
            this.cause = cause;
            return this;
        }
        
        /* -------------------------------------------------- Build -------------------------------------------------- */
        
        public IterationException build() {
            return new IterationExceptionSubclass(cause);
        }
        
    }
    
    public static InnerIterationExceptionBuilder withCause(Exception cause) {
        return new InnerIterationExceptionBuilder().withCause(cause);
    }
    
}
