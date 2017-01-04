package net.digitalid.utility.errors;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.type.Utility;

@Utility
@SuppressWarnings("null")
public class SupportErrorBuilder {
    
    public interface CauseSupportErrorBuilder {
        
        @Chainable
        public @Nonnull InnerSupportErrorBuilder withCause(@Nonnull Throwable cause);
        
    }
    
    public static class InnerSupportErrorBuilder implements CauseSupportErrorBuilder {
        
        private InnerSupportErrorBuilder() {
        }
        
        /* -------------------------------------------------- Message -------------------------------------------------- */
        
        private @Nonnull String message = null;
        
        @Chainable
        public @Nonnull InnerSupportErrorBuilder withMessage(@Nonnull String message) {
            this.message = message;
            return this;
        }
        
        /* -------------------------------------------------- Cause -------------------------------------------------- */
        
        private @Nonnull Throwable cause = null;
        
        @Chainable
        public @Nonnull InnerSupportErrorBuilder withCause(@Nonnull Throwable cause) {
            this.cause = cause;
            return this;
        }
        
        /* -------------------------------------------------- Build -------------------------------------------------- */
        
        public SupportError build() {
            return new SupportErrorSubclass(message, cause);
        }
        
    }
    
    public static CauseSupportErrorBuilder withMessage(@Nonnull String message) {
        return new InnerSupportErrorBuilder().withMessage(message);
    }
    
}
