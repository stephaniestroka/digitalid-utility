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
package net.digitalid.utility.functional.iterables;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This interface extends the finite iterable interface to provide a faster {@link #size()} implementation.
 * 
 * @see CollectionBasedIterable
 */
@ReadOnly
public interface CollectionIterable<@Specifiable ELEMENT> extends FiniteIterable<ELEMENT> {
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Pure
    @Override
    public default boolean isEmpty() {
        return size() == 0;
    }
    
    @Pure
    @Override
    public default @NonNegative int size(@Positive int limit) {
        return Math.min(size(), limit);
    }
    
    @Pure
    @Override
    public @NonNegative int size();
    
}
