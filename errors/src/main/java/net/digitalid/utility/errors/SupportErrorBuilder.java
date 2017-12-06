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
