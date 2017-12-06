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

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.method.PureWithSideEffects;
import net.digitalid.utility.rootclass.RootInterface;
import net.digitalid.utility.validation.annotations.generation.Default;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.string.CodeIdentifier;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This type models a node in the storage tree.
 * 
 * @see Module
 * @see Table
 * 
 * @see StorageImplementation
 */
@Mutable
public interface Storage extends RootInterface {
    
    /* -------------------------------------------------- Parent Module -------------------------------------------------- */
    
    /**
     * Returns the parent module to which this storage belongs.
     */
    @Pure
    @Default("null") // TODO: This should not be necessary but it still is.
    public @Nullable Module getParentModule();
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    /**
     * Returns the name of this storage.
     */
    @Pure
    public @Nonnull @CodeIdentifier @MaxSize(63) String getName();
    
    /**
     * Returns the full name of this storage with the given delimiter between the names of the parent modules in the given direction.
     */
    @Pure
    public default @Nonnull String getFullName(@Nonnull String delimiter, boolean rootToLeaf) {
        final @Nullable Module parentModule = getParentModule();
        if (parentModule != null) {
            final @Nonnull String parentName = parentModule.getFullName(delimiter, rootToLeaf);
            return rootToLeaf ? parentName + delimiter + getName() : getName() + delimiter + parentName;
        } else {
            return getName();
        }
    }
    
    /**
     * Returns the full name of this storage with periods between the names of the parent modules from leaf to root.
     */
    @Pure
    // TODO: @Cached
    public default @Nonnull String getFullNameWithPeriods() {
        return getFullName(".", false);
    }
    
    /**
     * Returns the full name of this storage with underlines between the names of the parent modules from root to leaf.
     */
    @Pure
    // TODO: @Cached
    public default @Nonnull @CodeIdentifier String getFullNameWithUnderlines() {
        return getFullName("_", true);
    }
    
    /* -------------------------------------------------- Visitor -------------------------------------------------- */
    
    /**
     * Accepts the given visitor with the given parameter and returns the result of the visitor.
     */
    @PureWithSideEffects
    public <@Specifiable RESULT, @Specifiable PARAMETER, @Unspecifiable EXCEPTION extends Exception> RESULT accept(@Nonnull StorageVisitor<RESULT, PARAMETER, EXCEPTION> visitor, PARAMETER parameter) throws EXCEPTION;
    
    /**
     * Accepts the given simple visitor.
     */
    @PureWithSideEffects
    public default <@Unspecifiable EXCEPTION extends Exception> void accept(@Nonnull SimpleStorageVisitor<EXCEPTION> visitor) throws EXCEPTION {
        accept(visitor, null);
    }
    
}
