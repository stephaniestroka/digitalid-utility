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
package net.digitalid.utility.rootclass;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.casting.Castable;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.validatable.Validatable;

/**
 * All custom interfaces in the Digital ID Library extend this root interface.
 * 
 * @see RootClass
 */
@Mutable
public interface RootInterface extends Castable, Validatable {
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    // The following methods will always be implemented by classes but are important when generating an implementation directly from an interface.
    
    @Pure
    @Override
    public boolean equals(@Nullable Object object);
    
    @Pure
    @Override
    public int hashCode();
    
    @Pure
    @Override
    public @Nonnull String toString();
    
}
