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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This logging filter accepts a message if any of its rules matches.
 * 
 * @see ConfigurationBasedLoggingFilter
 */
@Mutable
public class RuleBasedLoggingFilter extends LoggingFilter {
    
    /* -------------------------------------------------- Rules -------------------------------------------------- */
    
    private @Nonnull FiniteIterable<@Nonnull LoggingRule> rules;
    
    /**
     * Returns the rules that are used to filter the messages.
     */
    @Pure
    public @Nonnull FiniteIterable<@Nonnull LoggingRule> getRules() {
        return rules;
    }
    
    /**
     * Sets the rules that are used to filter the messages.
     */
    @Impure
    protected void setRules(@Nonnull FiniteIterable<@Nonnull LoggingRule> rules) {
        this.rules = rules;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected RuleBasedLoggingFilter(@Nonnull @NonNullableElements LoggingRule... rules) {
        this.rules = FiniteIterable.of(rules);
    }
    
    /**
     * Returns a logging filter with the given rules.
     */
    @Pure
    public static @Nonnull RuleBasedLoggingFilter with(@Nonnull @NonNullableElements LoggingRule... rules) {
        return new RuleBasedLoggingFilter(rules);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean isPotentiallyLogged(@Nonnull Level level) {
        return rules.matchAny(rule -> level.getValue() >= rule.getThreshold().getValue());
    }
    
    @Pure
    @Override
    public boolean isLogged(@Nonnull Level level, @Nonnull String caller, @Nonnull String thread, @Nonnull String message, @Nullable Throwable throwable) {
        return rules.matchAny(rule -> rule.accepts(level, caller, thread, message));
    }
    
}
