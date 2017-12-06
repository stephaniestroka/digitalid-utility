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
package net.digitalid.utility.interfaces;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.Functional;

/**
 * Classes that have a size are countable.
 */
@Functional
public interface Countable {
    
    /**
     * Returns the number of elements in this countable.
     */
    @Pure
    public @NonNegative int size();
    
    /**
     * Returns whether this countable is empty.
     */
    @Pure
    public default boolean isEmpty() {
        return size() == 0;
    }
    
    /**
     * Returns whether this countable is single.
     */
    @Pure
    public default boolean isSingle() {
        return size() == 1;
    }
    
    /**
     * Returns whether this countable is empty or single.
     */
    @Pure
    public default boolean isEmptyOrSingle() {
        return isEmpty() || isSingle();
    }
    
}
