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

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a flattening iterator that iterates over the elements of the given iterator with all collections up to the given level flattened.
 */
@Mutable
public class FlatteningIterator<@Specifiable OUTPUT, @Specifiable INPUT> extends SingleIteratorBasedIterator<OUTPUT, INPUT> {
    
    /* -------------------------------------------------- Level -------------------------------------------------- */
    
    protected final @NonNegative int level;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected FlatteningIterator(@Captured @Nonnull Iterator<? extends INPUT> primaryIterator, @NonNegative int level) {
        super(primaryIterator);
        
        this.level = level;
    }
    
    /**
     * Returns a new flattening iterator that iterates over the elements of the given iterator with all collections up to the given level flattened.
     */
    @Pure
    public static @Capturable <@Specifiable OUTPUT, @Specifiable INPUT> @Nonnull FlatteningIterator<OUTPUT, INPUT> with(@Captured @Nonnull Iterator<? extends INPUT> iterator, @NonNegative int level) {
        return new FlatteningIterator<>(iterator, level);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    private @Nullable INPUT nextElement = null;
    
    private boolean found = false;
    
    private @Nullable Iterator<OUTPUT> subiterator = null;
    
    @Pure
    @Override
    public boolean hasNext() {
        if (subiterator != null) {
            if (subiterator.hasNext()) {
                return true;
            } else {
                subiterator = null;
            }
        }
        
        assert subiterator == null;
        
        if (found) {
            return true;
        } else {
            while (primaryIterator.hasNext()) {
                final INPUT element = primaryIterator.next();
                if (level > 0) {
                    final FiniteIterable<?> iterable;
                    if (element instanceof Collection<?>) { iterable = FiniteIterable.of((Collection<?>) element); }
                    else if (element instanceof Object[]) { iterable = FiniteIterable.of((Object[]) element); }
                    else { iterable = null; }
                    if (iterable != null) {
                        subiterator = new FlatteningIterator<>(iterable.iterator(), level - 1);
                        if (subiterator.hasNext()) {
                            return true;
                        } else {
                            subiterator = null;
                            continue;
                        }
                    }
                }
                nextElement = element;
                found = true;
                return true;
            }
            return false;
        }
    }
    
    @Impure
    @Override
    @SuppressWarnings("unchecked")
    public @NonCapturable OUTPUT next() {
        if (hasNext()) {
            if (subiterator != null) {
                return subiterator.next();
            } else {
                found = false;
                return (OUTPUT) nextElement;
            }
        } else {
            throw new NoSuchElementException();
        }
    }
    
}
