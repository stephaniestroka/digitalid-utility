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
package net.digitalid.utility.functional.iterators;

import java.util.Iterator;
import java.util.Objects;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a read-only iterable iterator.
 */
@Mutable
public class ReadOnlyIterableIterator<@Specifiable ELEMENT> extends ReadOnlyIterator<ELEMENT> {
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    protected final @Nonnull Iterator<? extends ELEMENT> iterator;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ReadOnlyIterableIterator(@Captured @Nonnull Iterator<? extends ELEMENT> iterator) {
        this.iterator = Objects.requireNonNull(iterator);
    }
    
    /**
     * Returns a read-only iterable iterator that captures the given iterator.
     */
    @Pure
    public static @Capturable <@Specifiable ELEMENT> @Nonnull ReadOnlyIterableIterator<ELEMENT> with(@Captured @Nonnull Iterator<? extends ELEMENT> iterator) {
        return new ReadOnlyIterableIterator<>(iterator);
    }
    
    /* -------------------------------------------------- Delegated Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }
    
    @Impure
    @Override
    public ELEMENT next() {
        return iterator.next();
    }
    
}
