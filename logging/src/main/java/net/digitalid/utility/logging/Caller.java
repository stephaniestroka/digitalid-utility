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
package net.digitalid.utility.logging;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class determines the caller of a method.
 */
@Utility
public class Caller {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    /**
     * Stores the index of the caller of the logging method in the stack trace.
     */
    public static final @Nonnull Configuration<Integer> index = Configuration.with(5);
    
    /* -------------------------------------------------- Retrieval -------------------------------------------------- */
    
    /**
     * Returns the entry at the given index in the stack trace.
     */
    @Pure
    public static @Nonnull String get(int index) {
        final @Nonnull StackTraceElement element = Thread.currentThread().getStackTrace()[index];
        return element.getClassName() + "." + element.getMethodName() + ":" + element.getLineNumber();
    }
    
    /**
     * Returns the caller of the logging method.
     */
    @Pure
    public static @Nonnull String get() {
        return get(index.get());
    }
    
}
