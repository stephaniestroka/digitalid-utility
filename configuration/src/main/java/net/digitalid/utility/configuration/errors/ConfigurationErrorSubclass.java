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

import javax.annotation.Generated;
import javax.annotation.Nonnull;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.contracts.Ensure;

@SuppressWarnings("null")
@Generated(value = "net.digitalid.utility.processor.generator.JavaFileGenerator")
class ConfigurationErrorSubclass extends ConfigurationError {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    private final @Nonnull Configuration<?> configuration;
    
    @Override
    public @Nonnull Configuration<?> getConfiguration() {
        @Nonnull Configuration<?> result = this.configuration;
        Ensure.that(result != null).orThrow("The result may not be null.", result);
        Ensure.that(result != null).orThrow("The result may not be null.", result);
        return result;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    ConfigurationErrorSubclass(@Nonnull Configuration<?> configuration) {
        super();
        
        this.configuration = configuration;
    }
    
    /* -------------------------------------------------- Overridden Methods -------------------------------------------------- */
    
    @Override
    public String getSummary() {
        String result = super.getSummary();
        return result;
    }
    
    @Override
    public Throwable getCause() {
        Throwable result = super.getCause();
        return result;
    }
    
    @Override
    public @Nonnull String getMessage() {
        @Nonnull String result = super.getMessage();
        Ensure.that(result != null).orThrow("The result may not be null.", result);
        Ensure.that(result != null).orThrow("The result may not be null.", result);
        return result;
    }
    
    /* -------------------------------------------------- Implement Methods -------------------------------------------------- */
    
    /* -------------------------------------------------- Generated Methods -------------------------------------------------- */
    
}
