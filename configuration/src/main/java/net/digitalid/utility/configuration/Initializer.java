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
package net.digitalid.utility.configuration;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * An initializer initializes a certain configuration (its target).
 * It can require that other configurations are initialized before.
 */
@Stateless
public abstract class Initializer {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates and registers this initializer with the given target and dependencies.
     */
    @SuppressWarnings("null")
    protected Initializer(@NonCaptured @Modified @Nonnull Configuration<?> target, @NonCaptured @Unmodified @Nonnull @NonNullableElements Configuration<?>... dependencies) {
        Require.that(target != null).orThrow("The target may not be null.");
        Require.that(dependencies != null).orThrow("The dependencies may not be null.");
        
        target.addInitializer(this);
        for (@Nonnull Configuration<?> dependency : dependencies) {
            Require.that(dependency != null).orThrow("Each dependency may not be null.");
            target.addDependency(dependency);
        }
    }
    
    /* -------------------------------------------------- Execution -------------------------------------------------- */
    
    /**
     * Executes this initializer.
     * This method is only executed once.
     * 
     * @throws Exception if any problems occur.
     */
    @Impure
    protected abstract void execute() throws Exception;
    
}
