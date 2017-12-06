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
package net.digitalid.utility.property;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.method.PureWithSideEffects;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.interfaces.Locking;
import net.digitalid.utility.property.map.ReadOnlyMapProperty;
import net.digitalid.utility.property.set.ReadOnlySetProperty;
import net.digitalid.utility.property.value.ReadOnlyValueProperty;
import net.digitalid.utility.rootclass.RootInterface;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * A property is an object that can be {@link Observer observed}.
 * 
 * @see ReadOnlyMapProperty
 * @see ReadOnlySetProperty
 * @see ReadOnlyValueProperty
 * @see PropertyImplementation
 */
@ReadOnly // This interface is mutable regarding the observers but read-only properties must be able to inherit from this interface.
@ThreadSafe
public interface Property<@Unspecifiable OBSERVER extends Observer> extends RootInterface, Locking {
    
    /* -------------------------------------------------- Observers -------------------------------------------------- */
    
    /**
     * Registers the given observer with this property.
     * Use {@link #registerOnGuiThread(net.digitalid.utility.property.Observer)} instead if the observer updates the GUI
     * or {@link #registerOnNewThread(net.digitalid.utility.property.Observer)} if the update can take a lot of time.
     * 
     * @return whether the given observer was not already registered.
     */
    @PureWithSideEffects
    public boolean register(@Captured @Nonnull OBSERVER observer);
    
    /**
     * Registers the given observer with this property, which will then be notified on the GUI thread in the order of the updates.
     * 
     * @return whether the given observer was not already registered.
     */
    @PureWithSideEffects
    public boolean registerOnGuiThread(@Captured @Nonnull OBSERVER observer);
    
    /**
     * Registers the given observer with this property, which will then be notified on a separate thread in sequential order.
     * 
     * @return whether the given observer was not already registered.
     */
    @PureWithSideEffects
    public boolean registerOnNewThread(@Captured @Nonnull OBSERVER observer);
    
    /**
     * Deregisters the given observer with this property.
     * 
     * @return whether the given observer was actually registered.
     */
    @PureWithSideEffects
    public boolean deregister(@NonCaptured @Nonnull OBSERVER observer);
    
    /**
     * Returns whether the given observer is registered with this property.
     */
    @Pure
    public boolean isRegistered(@NonCaptured @Nonnull OBSERVER observer);
    
}
