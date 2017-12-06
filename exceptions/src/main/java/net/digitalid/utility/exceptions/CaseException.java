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

/**
 * A case exception indicates that a case is not covered because it was added after the control flow had been implemented.
 * For example, a case exception is thrown if a switch statement over an enumeration needs a default case.
 */
@Immutable
public abstract class CaseException extends InternalException {
    
    /* -------------------------------------------------- Variable -------------------------------------------------- */
    
    /**
     * Returns the name of the variable with the unexpected value.
     */
    @Pure
    public abstract @Nonnull String getVariable();
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the value of the given variable that was not expected.
     */
    @Pure
    public abstract @Nullable Object getValue();
    
    /* -------------------------------------------------- Message -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String getMessage() {
        return "The case where '" + getVariable() + "' is '" + getValue() + "' is not covered.";
    }
    
}
