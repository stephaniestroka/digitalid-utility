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
package net.digitalid.utility.configuration.errors;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.errors.InitializationError;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A dependency error indicates that the configurations have cyclic dependencies.
 */
@Immutable
public abstract class DependencyError extends InitializationError {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    /**
     * Returns the configuration to which the dependency should have been added.
     */
    @Pure
    public abstract @Nonnull Configuration<?> getConfiguration();
    
    /* -------------------------------------------------- Dependency -------------------------------------------------- */
    
    /**
     * Returns the dependency which should have been added to the configuration.
     */
    @Pure
    public abstract @Nonnull Configuration<?> getDependency();
    
    /* -------------------------------------------------- Message -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String getMessage() {
        return Strings.format("Could not add $ as a dependency of $, as $ already depends on $.", getDependency(), getConfiguration(), getDependency(), getConfiguration(), getDependency().getDependencyChainAsString(getConfiguration()));
    }
    
}
