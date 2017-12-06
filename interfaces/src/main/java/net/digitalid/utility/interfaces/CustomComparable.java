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

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Functional;

/**
 * This interface extends the {@link Comparable} interface with default comparisons.
 */
@Functional
public interface CustomComparable<T extends CustomComparable<T>> extends Comparable<T> {
    
    /* -------------------------------------------------- Comparison -------------------------------------------------- */
    
    @Pure
    @Override
    public int compareTo(@Nonnull T object);
    
    /* -------------------------------------------------- Comparisons -------------------------------------------------- */
    
    /**
     * Returns whether this object is equal to the given object.
     */
    @Pure
    public default boolean isEqualTo(@Nonnull T object) {
        return compareTo(object) == 0;
    }
    
    /**
     * Returns whether this object is greater than the given object.
     */
    @Pure
    public default boolean isGreaterThan(@Nonnull T object) {
        return compareTo(object) > 0;
    }
    
    /**
     * Returns whether this object is greater than or equal to the given object.
     */
    @Pure
    public default boolean isGreaterThanOrEqualTo(@Nonnull T object) {
        return compareTo(object) >= 0;
    }
    
    /**
     * Returns whether this object is less than the given object.
     */
    @Pure
    public default boolean isLessThan(@Nonnull T object) {
        return compareTo(object) < 0;
    }
    
    /**
     * Returns whether this object is less than or equal to the given object.
     */
    @Pure
    public default boolean isLessThanOrEqualTo(@Nonnull T object) {
        return compareTo(object) <= 0;
    }
    
}
