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
package net.digitalid.utility.logging.logger;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a logger that logs the messages to the standard output.
 */
@Mutable
public class StandardOutputLogger extends PrintStreamLogger {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a standard output logger that logs the messages to the standard output.
     */
    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    protected StandardOutputLogger() {
        super(System.out);
    }
    
    /**
     * Returns a standard output logger that logs the messages to the standard output.
     */
    @Pure
    public static @Capturable @Nonnull StandardOutputLogger withNoArguments() {
        return new StandardOutputLogger();
    }
    
}
