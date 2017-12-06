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
package net.digitalid.utility.collaboration.enumerations;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class enumerates the authors of the Digital ID library.
 */
@Immutable
public enum Author {
    
    ANYONE("Anyone", "info@digitalid.net"),
    
    KASPAR_ETTER("Kaspar Etter", "kaspar.etter@digitalid.net"),
    
    STEPHANIE_STROKA("Stephanie Stroka", "stephanie.stroka@digitalid.net");
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    private final @Nonnull String name;
    
    /**
     * Returns the name of this author.
     */
    // @Pure
    public @Nonnull String getName() {
        return name;
    }
    
    /* -------------------------------------------------- Email -------------------------------------------------- */
    
    private final @Nonnull String email;
    
    /**
     * Returns the email address of this author.
     */
    // @Pure
    public @Nonnull String getEmail() {
        return email;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    private Author(@Nonnull String name, @Nonnull String email) {
        this.name = name;
        this.email = email;
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    // @Pure
    @Override
    public @Nonnull String toString() {
        return getName();
    }
    
}
