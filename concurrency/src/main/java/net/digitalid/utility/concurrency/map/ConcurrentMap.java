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
package net.digitalid.utility.concurrency.map;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * Extends Java's {@link java.util.concurrent.ConcurrentMap ConcurrentMap} interface with useful methods.
 * 
 * @param <K> the type of the keys of this map.
 * @param <V> the type of the values of this map.
 * 
 * @see ConcurrentHashMap
 */
@Mutable
public interface ConcurrentMap<K, V> extends java.util.concurrent.ConcurrentMap<K, V>, Cloneable {
    
    /**
     * Associates the given value with the given key, if the
     * given key is not already associated with another value.
     * 
     * @return the value that is now associated with the given key.
     */
    @Impure
    public @NonCapturable @Nonnull V putIfAbsentElseReturnPresent(@Captured @Nonnull K key, @Captured @Nonnull V value);
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    /**
     * Returns a shallow copy of this map (the keys and values themselves are not cloned).
     */
    @Pure
    public @Capturable @Nonnull ConcurrentMap<K, V> clone();
    
}
