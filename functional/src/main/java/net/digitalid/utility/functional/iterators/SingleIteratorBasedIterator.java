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
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements an iterator which is based on a single iterator.
 * 
 * @see DoubleIteratorBasedIterator
 */
@Mutable
public abstract class SingleIteratorBasedIterator<@Specifiable OUTPUT, @Specifiable INPUT0> extends ReadOnlyIterator<OUTPUT> {
    
    /* -------------------------------------------------- Primary Iterator -------------------------------------------------- */
    
    /**
     * Stores the primary iterator on which this iterator is based.
     */
    protected final @Nonnull Iterator<? extends INPUT0> primaryIterator;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected SingleIteratorBasedIterator(@Captured @Nonnull Iterator<? extends INPUT0> primaryIterator) {
        this.primaryIterator = primaryIterator;
    }
    
}
