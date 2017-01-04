package net.digitalid.utility.exceptions;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.type.Utility;

@Utility
@SuppressWarnings("null")
public class UncheckedExceptionBuilder {
    
    public static class InnerUncheckedExceptionBuilder {
        
        private InnerUncheckedExceptionBuilder() {
        }
        
        /* -------------------------------------------------- Cause -------------------------------------------------- */
        
        private @Nonnull Exception cause = null;
        
        @Chainable
        public @Nonnull InnerUncheckedExceptionBuilder withCause(@Nonnull Exception cause) {
            this.cause = cause;
            return this;
        }
        
        /* -------------------------------------------------- Build -------------------------------------------------- */
        
        public UncheckedException build() {
            return new UncheckedExceptionSubclass(cause);
        }
        
    }
    
    public static InnerUncheckedExceptionBuilder withCause(@Nonnull Exception cause) {
        return new InnerUncheckedExceptionBuilder().withCause(cause);
    }
    
}
