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
