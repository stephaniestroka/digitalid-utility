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
 * Numerical values can be validated with generated contracts.
 */
@Functional
public interface LongNumerical<T extends LongNumerical<T>> extends CustomComparable<T> {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the value of this numerical.
     */
    @Pure
    public long getValue();
    
    /* -------------------------------------------------- Comparison -------------------------------------------------- */
    
    @Pure
    @Override
    public default int compareTo(@Nonnull T object) {
        return Long.compare(getValue(), object.getValue());
    }
    
    /* -------------------------------------------------- Comparisons -------------------------------------------------- */
    
    /**
     * Returns whether this numerical is negative.
     */
    @Pure
    public default boolean isNegative() {
        return getValue() < 0;
    }
    
    /**
     * Returns whether this numerical is non-negative.
     */
    @Pure
    public default boolean isNonNegative() {
        return getValue() >= 0;
    }
    
    /**
     * Returns whether this numerical is positive.
     */
    @Pure
    public default boolean isPositive() {
        return getValue() > 0;
    }
    
    /**
     * Returns whether this numerical is non-positive.
     */
    @Pure
    public default boolean isNonPositive() {
        return getValue() <= 0;
    }
    
}
