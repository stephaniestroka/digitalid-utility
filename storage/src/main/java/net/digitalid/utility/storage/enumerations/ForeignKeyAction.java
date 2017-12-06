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
package net.digitalid.utility.storage.enumerations;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This type enumerates the various actions that can be specified for when a referenced entry is updated or deleted.
 * (Once the storage artifact can be moved to the database project again, move this enum to a new enumerations artifact because the SQLReferenceOption interface of the dialect also depends on this.)
 */
@Immutable
public enum ForeignKeyAction {
    
    /* -------------------------------------------------- Constants -------------------------------------------------- */
    
    /**
     * Rejects the delete or update operation for the parent table.
     */
    RESTRICT,
    
    /**
     * Propagates the delete or update operation to the child table.
     */
    CASCADE,
    
    /**
     * Sets the foreign key column or columns in the child table to NULL.
     */
    SET_NULL,
    
    /**
     * The same as {@link #RESTRICT} (at least in case of MySQL).
     */
    NO_ACTION;
    
    /* -------------------------------------------------- String -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return name().replace("_", " ");
    }
    
}
