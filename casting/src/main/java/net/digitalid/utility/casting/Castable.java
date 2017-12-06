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
package net.digitalid.utility.casting;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This interface provides an easy way to cast an object to a subclass.
 */
@Mutable
public interface Castable {
    
    /* -------------------------------------------------- Casting -------------------------------------------------- */
    
    /**
     * Casts this object to the given target class.
     * 
     * @require targetClass.isInstance(this) : "This object is an instance of the given target class.";
     */
    @Pure
    public default @Chainable <T> @Nonnull T castTo(@Nonnull Class<T> targetClass) {
        Require.that(targetClass.isInstance(this)).orThrow("This object $ has to be an instance of the target class $.", this, targetClass);
        
        return targetClass.cast(this);
    }
    
}
