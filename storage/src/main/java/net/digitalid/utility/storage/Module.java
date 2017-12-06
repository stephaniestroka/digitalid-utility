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

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.method.PureWithSideEffects;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class models an inner node in the storage tree.
 */
@Mutable
public abstract class Module extends StorageImplementation {
    
    /* -------------------------------------------------- Substorages -------------------------------------------------- */
    
    private final @Nonnull @NonNullableElements Set<Storage> childStorages = new LinkedHashSet<>();
    
    /**
     * Returns the child storages of this storage.
     */
    @Pure
    public @Nonnull @NonNullableElements FiniteIterable<Storage> getChildStorages() {
        return FiniteIterable.of(childStorages);
    }
    
    /**
     * Registers the given child storage at this storage.
     */
    @Impure
    void addChildStorage(@Nonnull Storage childStorage) {
        childStorages.add(childStorage);
    }
    
    /* -------------------------------------------------- Visitor -------------------------------------------------- */
    
    @Override
    @PureWithSideEffects
    public <@Specifiable RESULT, @Specifiable PARAMETER, @Unspecifiable EXCEPTION extends Exception> RESULT accept(@Nonnull StorageVisitor<RESULT, PARAMETER, EXCEPTION> visitor, PARAMETER parameter) throws EXCEPTION {
        return visitor.visit(this, parameter);
    }
    
}
