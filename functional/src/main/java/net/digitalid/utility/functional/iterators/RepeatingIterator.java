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

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a repeating iterator that repeats the given element infinitely.
 */
@Mutable
public class RepeatingIterator<@Specifiable ELEMENT> extends InfiniteIterator<ELEMENT> {
    
    /* -------------------------------------------------- Element -------------------------------------------------- */
    
    protected final ELEMENT element;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected RepeatingIterator(@Captured ELEMENT element) {
        this.element = element;
    }
    
    /**
     * Returns a new repeating iterator that repeats the given element infinitely.
     */
    @Pure
    public static @Capturable <@Specifiable ELEMENT> @Nonnull RepeatingIterator<ELEMENT> with(@Captured ELEMENT element) {
        return new RepeatingIterator<>(element);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Impure
    @Override
    public @NonCapturable ELEMENT next() {
        return element;
    }
    
}
