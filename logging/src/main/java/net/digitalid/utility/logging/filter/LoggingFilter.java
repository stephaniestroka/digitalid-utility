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

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * Instances of this class are used to filter log messages.
 * 
 * @see LevelBasedLoggingFilter
 * @see RuleBasedLoggingFilter
 */
@Mutable
public abstract class LoggingFilter {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    /**
     * Stores the filter which is used to filter the log messages.
     */
    public static final @Nonnull Configuration<LoggingFilter> filter = Configuration.with(LevelBasedLoggingFilter.with(Level.INFORMATION));
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    /**
     * Returns whether a message with the given level is potentially logged.
     */
    @Pure
    public abstract boolean isPotentiallyLogged(@Nonnull Level level);
    
    /**
     * Returns whether the given message with the given arguments is (to be) logged.
     */
    @Pure
    public abstract boolean isLogged(@Nonnull Level level, @Nonnull String caller, @Nonnull String thread, @Nonnull String message, @Nullable Throwable throwable);
    
}
