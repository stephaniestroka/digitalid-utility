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
package net.digitalid.utility.logging.filter;

import java.io.File;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.file.Files;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This logging filter loads its rules from a configuration file.
 */
@Mutable
public class ConfigurationBasedLoggingFilter extends RuleBasedLoggingFilter {
    
    /* -------------------------------------------------- File -------------------------------------------------- */
    
    private final @Nonnull File file;
    
    /**
     * Returns the configuration file.
     */
    @Pure
    public @Nonnull File getFile() {
        return file;
    }
    
    /* -------------------------------------------------- Loading -------------------------------------------------- */
    
    /**
     * Reloads the logging rules from the configuration file.
     * 
     * @throws IllegalArgumentException if a rule has an invalid level.
     */
    @Impure
    public void reload() throws IllegalArgumentException {
        setRules(Files.readNonCommentNonEmptyTrimmedLines(file).map(LoggingRule::decode).evaluate());
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Stores the comments at the beginning of each configuration file.
     */
    private final @Nonnull FiniteIterable<@Nonnull String> comments = FiniteIterable.of("# Only messages that match one of the following rules are logged.", "# There is one rule per line written in the following format:", "# Level-Threshold; Caller-Prefix; Thread-Prefix; Message-Regex", "# When skipping all subsequent tokens, the semicolons are optional.");
    
    protected ConfigurationBasedLoggingFilter(@Nonnull File file, @Nonnull @NonNullableElements LoggingRule... defaultRules) throws IllegalArgumentException {
        super(defaultRules);
        
        this.file = file;
        if (file.exists()) {
            reload();
        } else {
            Files.write(comments.combine(getRules().map(LoggingRule::encode)), file);
        }
    }
    
    /**
     * Returns a logging filter with the given configuration file and default rules.
     * 
     * @throws IllegalArgumentException if a rule has an invalid level.
     */
    @Pure
    public static @Nonnull ConfigurationBasedLoggingFilter with(@Nonnull File file, @Nonnull @NonNullableElements LoggingRule... defaultRules) throws IllegalArgumentException {
        return new ConfigurationBasedLoggingFilter(file, defaultRules);
    }
    
}
