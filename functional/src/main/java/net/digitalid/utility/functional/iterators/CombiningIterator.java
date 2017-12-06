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
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a combining iterator that iterates first over the elements of the first iterator and then over the elements of the second iterator.
 */
@Mutable
public class CombiningIterator<@Specifiable ELEMENT> extends DoubleIteratorBasedIterator<ELEMENT, ELEMENT, ELEMENT> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected CombiningIterator(@Captured @Nonnull Iterator<? extends ELEMENT> primaryIterator, @Captured @Nonnull Iterator<? extends ELEMENT> secondaryIterator) {
        super(primaryIterator, secondaryIterator);
    }
    
    /**
     * Returns a new combining iterator that iterates first over the elements of the first iterator and then over the elements of the second iterator.
     */
    @Pure
    public static @Capturable <@Specifiable ELEMENT> @Nonnull CombiningIterator<ELEMENT> with(@Captured @Nonnull Iterator<? extends ELEMENT> primaryIterator, @Captured @Nonnull Iterator<? extends ELEMENT> secondaryIterator) {
        return new CombiningIterator<>(primaryIterator, secondaryIterator);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean hasNext() {
        return primaryIterator.hasNext() || secondaryIterator.hasNext();
    }
    
    @Impure
    @Override
    public @NonCapturable ELEMENT next() {
        if (primaryIterator.hasNext()) { return primaryIterator.next(); }
        else { return secondaryIterator.next(); }
    }
    
}
