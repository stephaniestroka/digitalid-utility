package net.digitalid.utility.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.type.Utility;

@Utility
@SuppressWarnings("null")
public class CaseExceptionBuilder {
    
    public static class InnerCaseExceptionBuilder {
        
        private InnerCaseExceptionBuilder() {
        }
        
        /* -------------------------------------------------- Variable -------------------------------------------------- */
        
        private @Nonnull String variable = null;
        
        @Chainable
        public @Nonnull InnerCaseExceptionBuilder withVariable(@Nonnull String variable) {
            this.variable = variable;
            return this;
        }
        
        /* -------------------------------------------------- Value -------------------------------------------------- */
        
        private @Nullable Object value = null;
        
        @Chainable
        public @Nonnull InnerCaseExceptionBuilder withValue(@Nullable Object value) {
            this.value = value;
            return this;
        }
        
        /* -------------------------------------------------- Build -------------------------------------------------- */
        
        public CaseException build() {
            return new CaseExceptionSubclass(variable, value);
        }
        
    }
    
    public static InnerCaseExceptionBuilder withVariable(@Nonnull String variable) {
        return new InnerCaseExceptionBuilder().withVariable(variable);
    }
    
}
