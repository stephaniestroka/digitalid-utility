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
package net.digitalid.utility.immutable.entry;

import java.util.Iterator;
import java.util.Map;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.iterators.ReadOnlyIterableIterator;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements an iterator that returns only read-only entries.
 */
@Mutable
public class ReadOnlyEntrySetIterator<K, V> extends ReadOnlyIterableIterator<Map.@Nonnull Entry<K, V>> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ReadOnlyEntrySetIterator(@Captured @Nonnull Iterator<? extends Map.@Nonnull Entry<K, V>> iterator) {
        super(iterator);
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    @Impure
    @Override
    public @Nonnull ReadOnlyEntry<K, V> next() {
        return new ReadOnlyEntry<>(super.next());
    }
    
}
