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
package net.digitalid.utility.initialization;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.circumfixes.Quotes;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.configuration.Initializer;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * This class adds logging capabilities to the {@link Initializer}.
 */
@Stateless
public abstract class LoggingInitializer extends Initializer {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates and registers this initializer with the given target and dependencies.
     */
    protected LoggingInitializer(@NonCaptured @Modified @Nonnull Configuration<?> target, @NonCaptured @Unmodified @Nonnull @NonNullableElements Configuration<?>... dependencies) {
        super(target, dependencies);
        
        Log.debugging("The initializer " + this + " was loaded.");
    }
    
    /* -------------------------------------------------- Execution -------------------------------------------------- */
    
    /**
     * Executes this initializer.
     * This method is only executed once.
     * 
     * @throws Exception if any problems occur.
     */
    @Impure
    protected abstract void executeWithoutLogging() throws Exception;
    
    @Impure
    @Override
    protected final void execute() throws Exception {
        Log.debugging("The initializer " + this + " begins its execution.");
        final long start = System.currentTimeMillis();
        executeWithoutLogging();
        final long end = System.currentTimeMillis();
        Log.debugging("The initializer " + this + " ends its execution in " + (end - start) + " ms.");
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return Quotes.inSingle(getClass().getSimpleName());
    }
    
}
