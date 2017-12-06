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

import java.util.ListIterator;
import java.util.Objects;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a read-only list iterator.
 */
@Mutable
public class ReadOnlyListIterator<@Specifiable ELEMENT> extends ReadOnlyIterableIterator<ELEMENT> implements ListIterator<ELEMENT> {
    
    /* -------------------------------------------------- List Iterator -------------------------------------------------- */
    
    protected final @Nonnull ListIterator<? extends ELEMENT> listIterator;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ReadOnlyListIterator(@Captured @Nonnull ListIterator<? extends ELEMENT> iterator) {
        super(iterator);
        
        this.listIterator = Objects.requireNonNull(iterator);
    }
    
    /**
     * Returns a read-only list iterator that captures the given list iterator.
     */
    @Pure
    public static @Capturable <@Specifiable ELEMENT> @Nonnull ReadOnlyListIterator<ELEMENT> with(@Captured @Nonnull ListIterator<? extends ELEMENT> iterator) {
        return new ReadOnlyListIterator<>(iterator);
    }
    
    /* -------------------------------------------------- Delegated Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean hasPrevious() {
        return listIterator.hasPrevious();
    }
    
    @Impure
    @Override
    public ELEMENT previous() {
        return listIterator.previous();
    }
    
    @Pure
    @Override
    public int nextIndex() {
        return listIterator.nextIndex();
    }
    
    @Pure
    @Override
    public int previousIndex() {
        return listIterator.previousIndex();
    }
    
    /* -------------------------------------------------- Unsupported Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public final void set(@NonCaptured @Unmodified ELEMENT e) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final void add(@NonCaptured @Unmodified ELEMENT e) {
        throw new UnsupportedOperationException();
    }
    
}
