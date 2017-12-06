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
import java.util.NoSuchElementException;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.functional.exceptions.IterationException;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class models an iterator whose elements cannot be removed.
 */
@Mutable
public abstract class ReadOnlyIterator<@Specifiable ELEMENT> implements Iterator<ELEMENT> {
    
    /* -------------------------------------------------- Supported Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public abstract boolean hasNext() throws IterationException;
    
    @Impure
    @Override
    public abstract ELEMENT next() throws IterationException, NoSuchElementException;
    
    /* -------------------------------------------------- Unsupported Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public final void remove() throws  UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    
}
