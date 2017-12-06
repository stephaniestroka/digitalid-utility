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
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements an array iterator that iterates over the given elements.
 */
@Mutable
public class ReadOnlyArrayIterator<@Specifiable ELEMENT> extends ReadOnlyIterator<ELEMENT> {
    
    /* -------------------------------------------------- Elements -------------------------------------------------- */
    
    protected final @Shared @Nonnull ELEMENT[] elements;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    @SafeVarargs
    protected ReadOnlyArrayIterator(@Shared @Unmodified @Nonnull ELEMENT... elements) {
        this.elements = elements;
    }
    
    /**
     * Returns a new read-only array iterator that iterates over the given elements.
     */
    @Pure
    @SafeVarargs
    public static @Capturable <@Specifiable ELEMENT> @Nonnull ReadOnlyArrayIterator<ELEMENT> with(@Shared @Unmodified @Nonnull ELEMENT... elements) {
        return new ReadOnlyArrayIterator<>(elements);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    private @NonNegative int cursor = 0;
    
    @Pure
    @Override
    public boolean hasNext() {
        return cursor < elements.length;
    }
    
    @Impure
    @Override
    public @NonCapturable ELEMENT next() {
        if (hasNext()) { return elements[cursor++]; }
        else { throw new NoSuchElementException(); }
    }
    
    /**
     * Returns the index of the next element.
     * 
     * @throws NoSuchElementException if there is no next element.
     */
    @Pure
    public @NonNegative int nextIndex() {
        if (hasNext()) { return cursor; }
        else { throw new NoSuchElementException(); }
    }
    
    /**
     * Returns whether this iterator has a previous element.
     */
    @Pure
    public boolean hasPrevious() {
        return cursor > 0;
    }
    
    /**
     * Returns the previous element of this iterator.
     */
    @Impure
    public @NonCapturable ELEMENT previous() {
        if (hasPrevious()) { return elements[--cursor]; }
        else { throw new NoSuchElementException(); }
    }
    
    /**
     * Returns the index of the previous element.
     * 
     * @throws NoSuchElementException if there is no previous element.
     */
    @Pure
    public @NonNegative int previousIndex() {
        if (hasPrevious()) { return cursor - 1; }
        else { throw new NoSuchElementException(); }
    }
    
}
