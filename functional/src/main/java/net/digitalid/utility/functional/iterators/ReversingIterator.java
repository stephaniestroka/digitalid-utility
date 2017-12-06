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

import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.Shared;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a reversing iterator that iterates over the given elements in reverse order.
 */
@Mutable
public class ReversingIterator<@Specifiable ELEMENT> extends ReadOnlyIterator<ELEMENT> {
    
    /* -------------------------------------------------- Elements -------------------------------------------------- */
    
    protected final @Nonnull ELEMENT[] elements;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    @SafeVarargs
    protected ReversingIterator(@Shared @Unmodified @Nonnull ELEMENT... elements) {
        this.elements = elements;
        this.cursor = elements.length - 1;
    }
    
    /**
     * Returns a new reversing iterator that iterates over the given elements in reverse order.
     */
    @Pure
    @SafeVarargs
    public static @Capturable <@Specifiable ELEMENT> @Nonnull ReversingIterator<ELEMENT> with(@Shared @Unmodified @Nonnull ELEMENT... elements) {
        return new ReversingIterator<>(elements);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    private int cursor;
    
    @Pure
    @Override
    public boolean hasNext() {
        return cursor >= 0;
    }
    
    @Impure
    @Override
    public @NonCapturable ELEMENT next() {
        if (hasNext()) { return elements[cursor--]; }
        else { throw new NoSuchElementException(); }
    }
    
}
