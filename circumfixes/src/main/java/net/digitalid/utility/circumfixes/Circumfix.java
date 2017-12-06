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
package net.digitalid.utility.circumfixes;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A circumfix surrounds a string with a prefix and a suffix.
 * 
 * @see Brackets
 * @see Comments
 * @see Quotes
 */
@Immutable
public interface Circumfix {
    
    /* -------------------------------------------------- Prefix -------------------------------------------------- */
    
    /**
     * Returns the prefix.
     */
    @Pure
    public @Nonnull String getPrefix();
    
    /* -------------------------------------------------- Suffix -------------------------------------------------- */
    
    /**
     * Returns the suffix.
     */
    @Pure
    public @Nonnull String getSuffix();
    
    /* -------------------------------------------------- Both -------------------------------------------------- */
    
    /**
     * Returns the prefix directly followed by the suffix.
     */
    @Pure
    public default @Nonnull String getBoth() {
        return getPrefix() + getSuffix();
    }
    
}
