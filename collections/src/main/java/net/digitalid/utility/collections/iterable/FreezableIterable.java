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
package net.digitalid.utility.collections.iterable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.collections.array.FreezableArray;
import net.digitalid.utility.collections.collection.FreezableCollection;
import net.digitalid.utility.contracts.exceptions.PreconditionException;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This interface models an {@link Iterable iterable} that can be {@link FreezableInterface frozen}.
 * As a consequence, all modifying methods may fail with a {@link PreconditionException}.
 * It is recommended to use only {@link ReadOnly} or {@link Immutable} types for the elements.
 * 
 * @see FreezableCollection
 * @see FreezableArray
 */
@Freezable(ReadOnlyIterable.class)
public interface FreezableIterable<E> extends ReadOnlyIterable<E>, FreezableInterface {
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    @Impure
    @Override
    @NonFrozenRecipient
    public @Chainable @Nonnull @Frozen ReadOnlyIterable<E> freeze();
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    /**
     * Returns whether this iterable contains all of the elements of the given collection.
     */
    @Pure
    public default boolean containsAll(@NonCaptured @Unmodified @Nonnull FreezableCollection<?> collection) {
        for (@Nullable Object element : collection) {
            if (!contains(element)) { return false; }
        }
        return true;
    }
    
}
