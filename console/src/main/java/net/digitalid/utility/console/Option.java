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
package net.digitalid.utility.console;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.console.exceptions.EscapeException;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * Every option in the {@link Console console} has to extend this class.
 */
@Immutable
public abstract class Option /* extends RootClass */ {
    
    /* -------------------------------------------------- Description -------------------------------------------------- */
    
    private final @Nonnull String description;
    
    /**
     * Returns the description of this option.
     */
    @Pure
    public @Nonnull String getDescription() {
        return description;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    protected Option(@Nonnull String description) {
        this.description = description;
    }
    
    /* -------------------------------------------------- Execution -------------------------------------------------- */
    
    /**
     * Executes this option.
     * 
     * @throws EscapeException if the user escapes.
     */
    @Pure
    public abstract void execute() throws EscapeException;
    
}
