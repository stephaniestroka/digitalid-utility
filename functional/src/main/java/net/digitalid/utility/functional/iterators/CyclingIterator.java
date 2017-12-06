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

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a cycling iterator that iterates over the elements of the given iterable indefinitely.
 */
@Mutable
public class CyclingIterator<@Specifiable ELEMENT> extends ReadOnlyIterator<ELEMENT> {
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    protected final @Nonnull FiniteIterable<? extends ELEMENT> iterable;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected CyclingIterator(@Nonnull FiniteIterable<? extends ELEMENT> iterable) {
        this.iterable = iterable;
        this.iterator = iterable.iterator();
        this.hasNext = iterator.hasNext();
    }
    
    /**
     * Returns a new cycling iterator that iterates over the elements of the given iterable indefinitely.
     */
    @Pure
    public static @Capturable <@Specifiable ELEMENT> @Nonnull CyclingIterator<ELEMENT> with(@Nonnull FiniteIterable<? extends ELEMENT> iterable) {
        return new CyclingIterator<>(iterable);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    private @Nonnull Iterator<? extends ELEMENT> iterator;
    
    private final boolean hasNext;
    
    @Pure
    @Override
    public boolean hasNext() {
        return hasNext;
    }
    
    @Impure
    @Override
    public @NonCapturable ELEMENT next() {
        if (!iterator.hasNext()) { iterator = iterable.iterator(); }
        return iterator.next();
    }
    
}
