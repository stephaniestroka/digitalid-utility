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
package net.digitalid.utility.storage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.PureWithSideEffects;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * A simpler interface to visit a {@link Storage}.
 */
@Stateless
@Functional
public interface SimpleStorageVisitor<@Unspecifiable EXCEPTION extends Exception> extends StorageVisitor<@Nullable Void, @Nullable Void, EXCEPTION> {
    
    /**
     * Visits the given table.
     */
    @PureWithSideEffects
    public void visit(@Nonnull Table<?, ?> table) throws EXCEPTION;
    
    @Override
    @PureWithSideEffects
    public default @Nullable Void visit(@Nonnull Table<?, ?> table, @Nullable Void parameter) throws EXCEPTION {
        visit(table);
        return null;
    }
    
}
