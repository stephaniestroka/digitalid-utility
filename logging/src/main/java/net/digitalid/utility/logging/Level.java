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
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class enumerates the various levels of log messages.
 * 
 * @see Log
 */
@Immutable
public enum Level {
    
    /* -------------------------------------------------- Constants -------------------------------------------------- */
    
    /**
     * The level for verbose messages that make it easier to trace program execution.
     */
    VERBOSE(0),
    
    /**
     * The level for debugging messages that help developers locate bugs in the code.
     */
    DEBUGGING(1),
    
    /**
     * The level for information messages that inform about important runtime events.
     */
    INFORMATION(2),
    
    /**
     * The level for warning messages that indicate potential problems in the program.
     */
    WARNING(3),
    
    /**
     * The level for error messages about malfunctions from which the application can possibly recover.
     */
    ERROR(4),
    
    /**
     * The level for fatal messages about malfunctions that prevent a thread or process from continuing.
     */
    FATAL(5),
    
    /**
     * The level to turn logging off.
     */
    OFF(6);
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    private final byte value;
    
    /**
     * Returns the byte representation of this level.
     */
    @Pure
    public byte getValue() {
        return value;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    private Level(int value) {
        this.value = (byte) value;
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return Strings.uppercaseFirstCharacter(name().toLowerCase());
    }
    
}
