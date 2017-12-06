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

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

@Immutable
class CaseExceptionSubclass extends CaseException {
    
    /* -------------------------------------------------- Variable -------------------------------------------------- */
    
    private final @Nonnull String variable;
    
    @Pure
    @Override
    public @Nonnull String getVariable() {
        return this.variable;
    }
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    private final @Nullable Object value;
    
    @Pure
    @Override
    public @Nullable Object getValue() {
        return this.value;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    CaseExceptionSubclass(@Nonnull String variable, @Nullable Object value) {
        this.variable = variable;
        this.value = value;
    }
    
}
