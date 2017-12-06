/*
 * Copyright (C) 2017 Synacts GmbH, Switzerland (info@synacts.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
