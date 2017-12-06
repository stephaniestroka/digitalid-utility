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
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a zipping iterator that iterates over the elements of the given iterators in pairs, extending the shorter or truncating the longer iterator depending on the given flag.
 */
@Mutable
public class ZippingIterator<@Specifiable INPUT0, @Specifiable INPUT1> extends DoubleIteratorBasedIterator<@Nonnull Pair<INPUT0, INPUT1>, INPUT0, INPUT1> {
    
    /* -------------------------------------------------- Shortest -------------------------------------------------- */
    
    protected final boolean shortest;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ZippingIterator(@Captured @Nonnull Iterator<? extends INPUT0> primaryIterator, @Captured @Nonnull Iterator<? extends INPUT1> secondaryIterator, boolean shortest) {
        super(primaryIterator, secondaryIterator);
        
        this.shortest = shortest;
    }
    
    /**
     * Returns a new zipping iterator that iterates over the elements of the given iterators in pairs, extending the shorter or truncating the longer iterator depending on the given flag.
     */
    @Pure
    public static @Capturable <@Specifiable INPUT0, @Specifiable INPUT1> @Nonnull ZippingIterator<INPUT0, INPUT1> with(@Captured @Nonnull Iterator<? extends INPUT0> primaryIterator, @Captured @Nonnull Iterator<? extends INPUT1> secondaryIterator, boolean shortest) {
        return new ZippingIterator<>(primaryIterator, secondaryIterator, shortest);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean hasNext() {
        if (shortest) { return primaryIterator.hasNext() && secondaryIterator.hasNext(); }
        else { return primaryIterator.hasNext() || secondaryIterator.hasNext(); }
    }
    
    @Impure
    @Override
    public @Nonnull Pair<INPUT0, INPUT1> next() {
        if (shortest) { return Pair.of(primaryIterator.next(), secondaryIterator.next()); }
        else { return Pair.of(primaryIterator.hasNext() || !secondaryIterator.hasNext() ? primaryIterator.next() : null, secondaryIterator.hasNext() || !primaryIterator.hasNext() ? secondaryIterator.next() : null); }
    }
    
}
