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
