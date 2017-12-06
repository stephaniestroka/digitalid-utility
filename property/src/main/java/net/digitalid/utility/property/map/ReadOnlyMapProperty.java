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
package net.digitalid.utility.property.map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.collaboration.enumerations.Priority;
import net.digitalid.utility.collections.map.ReadOnlyMap;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.property.Property;
import net.digitalid.utility.validation.annotations.type.ReadOnly;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This read-only property stores a map of key-value pairs.
 * 
 * @see WritableMapProperty
 * @see ReadOnlyVolatileMapProperty
 * @see ReadOnlyMapPropertyImplementation
 */
@ThreadSafe
@ReadOnly(WritableMapProperty.class)
@TODO(task = "Restrict the ReadOnlyMap to a SynchronizedReadOnlyMap.", date = "2016-09-27", author = Author.KASPAR_ETTER, assignee = Author.KASPAR_ETTER, priority = Priority.MIDDLE)
public interface ReadOnlyMapProperty<@Unspecifiable KEY, @Unspecifiable VALUE, @Unspecifiable READONLY_MAP extends ReadOnlyMap<@Nonnull @Valid("key") KEY, @Nonnull @Valid VALUE>, @Unspecifiable EXCEPTION1 extends Exception, @Unspecifiable EXCEPTION2 extends Exception, @Unspecifiable OBSERVER extends MapObserver<KEY, VALUE, READONLY_MAP, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY>, @Unspecifiable PROPERTY extends ReadOnlyMapProperty<KEY, VALUE, READONLY_MAP, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY>> extends Property<OBSERVER>, Valid.Key<KEY>, Valid.Value<VALUE> {
    
    /* -------------------------------------------------- Getters -------------------------------------------------- */
    
    /**
     * Returns a read-only map with the key-value pairs of this property.
     */
    @Pure
    public @Nonnull @NonFrozen READONLY_MAP get() throws EXCEPTION1, EXCEPTION2;
    
    /**
     * Returns the value for the given key of this property.
     */
    @Pure
    public @NonCapturable @Nullable @Valid VALUE get(@NonCaptured @Unmodified @Nonnull @Valid("key") KEY key) throws EXCEPTION1, EXCEPTION2;
    
}
