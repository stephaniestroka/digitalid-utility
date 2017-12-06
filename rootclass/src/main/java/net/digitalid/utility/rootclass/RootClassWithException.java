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
package net.digitalid.utility.rootclass;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.CallSuper;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * All custom classes in the Digital ID Library extend this root class.
 * 
 * @param <EXCEPTION> the type of exceptions that the {@link #initialize()} method may throw.
 */
@Mutable
public abstract class RootClassWithException<@Unspecifiable EXCEPTION extends Exception> implements RootInterface {
    
    /* -------------------------------------------------- Initialization -------------------------------------------------- */
    
    /**
     * Initializes this object after the constructors of the subclasses have been executed.
     */
    @Pure
    @CallSuper
    protected void initialize() throws EXCEPTION {}
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public abstract boolean equals(@Nullable Object object);
    
    @Pure
    @Override
    public abstract int hashCode();
    
    @Pure
    @Override
    public abstract @Nonnull String toString();
    
    /* -------------------------------------------------- Inheritance -------------------------------------------------- */
    
    /**
     * Returns the natively implemented hash code of the {@link Object} class.
     * (Java does not allow to call the implementation of a super super class.)
     */
    @Pure
    protected int getHashCode() {
        return super.hashCode();
    }
    
}
