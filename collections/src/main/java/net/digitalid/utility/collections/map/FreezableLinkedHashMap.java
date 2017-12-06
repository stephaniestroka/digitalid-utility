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
package net.digitalid.utility.collections.map;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.circumfixes.Brackets;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.collaboration.enumerations.Priority;
import net.digitalid.utility.collections.collection.BackedFreezableCollection;
import net.digitalid.utility.collections.collection.FreezableCollection;
import net.digitalid.utility.collections.set.BackedFreezableSet;
import net.digitalid.utility.collections.set.FreezableSet;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.immutable.entry.ReadOnlyEntrySet;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.generation.Default;
import net.digitalid.utility.validation.annotations.generation.Recover;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This class extends the {@link LinkedHashMap} and makes it {@link FreezableInterface freezable}.
 * It is recommended to use only {@link Immutable} types for the keys
 * and {@link ReadOnly} or {@link Immutable} types for the values.
 */
@GenerateBuilder
@GenerateSubclass
@Freezable(ReadOnlyMap.class)
public abstract class FreezableLinkedHashMap<K, V> extends LinkedHashMap<K, V> implements FreezableMap<K, V> {
    
    /* -------------------------------------------------- Fix this issue! -------------------------------------------------- */
    
    @Pure
    @Override
    @TODO(task = "For some reasons, the SubclassGenerator treats this method as abstract and generates a required field for it if this method is removed.", date = "2016-04-29", author = Author.KASPAR_ETTER, assignee = Author.STEPHANIE_STROKA, priority = Priority.HIGH)
    public boolean isEmpty() {
        return super.isEmpty();
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    @Recover
    protected FreezableLinkedHashMap(@NonNegative @Default("16") int initialCapacity, @Positive @Default("0.75f") float loadFactor, @Default("false") boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }
    
    protected FreezableLinkedHashMap(@NonCaptured @Unmodified @Nonnull Map<? extends K, ? extends V> map) {
        super(map);
    }
    
    /**
     * Returns a new freezable linked hash map with the mappings of the given map or null if the given map is null.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public static @Capturable <K, V> @NonFrozen FreezableLinkedHashMap<K, V> withMappingsOf(ReadOnlyMap<? extends K, ? extends V> map) {
        return map == null ? null : new FreezableLinkedHashMapSubclass<>((Map<? extends K, ? extends V>) map);
    }
    
    /**
     * Returns a new freezable linked hash map with the mappings of the given map or null if the given map is null.
     */
    @Pure
    public static @Capturable <K, V> @NonFrozen FreezableLinkedHashMap<K, V> withMappingsOf(@NonCaptured @Unmodified Map<? extends K, ? extends V> map) {
        return map == null ? null : new FreezableLinkedHashMapSubclass<>(map);
    }
    
    /**
     * Returns a new freezable linked hash map with the mappings of the given map or null if the given map is null.
     */
    @Pure
    public static @Capturable <K, V> @NonFrozen FreezableLinkedHashMap<K, V> withMappingsOf(@NonCaptured @Unmodified FreezableMap<? extends K, ? extends V> map) {
        return map == null ? null : new FreezableLinkedHashMapSubclass<>(map);
    }
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    private boolean frozen = false;
    
    @Pure
    @Override
    public boolean isFrozen() {
        return frozen;
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public @Chainable @Nonnull @Frozen ReadOnlyMap<K, V> freeze() {
        this.frozen = true;
        return this;
    }
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableLinkedHashMap<K,V> clone() {
        return new FreezableLinkedHashMapSubclass<>(this);
    }
    
    /* -------------------------------------------------- Entries -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonCapturable @Nonnull @NullableElements FreezableSet<K> keySet() {
        return BackedFreezableSet.with(this, super.keySet());
    }
    
    @Pure
    @Override
    public @NonCapturable @Nonnull @NullableElements FreezableCollection<V> values() {
        return BackedFreezableCollection.with(this, super.values());
    }
    
    @Pure
    @Override
    public @NonCapturable @Nonnull ReadOnlyEntrySet<K, V> entrySet() {
        return ReadOnlyEntrySet.with(super.entrySet());
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    @Impure
    @Override
    @NonFrozenRecipient
    public @Capturable @Nullable V put(@Captured K key, @Captured V value) {
        return super.put(key, value);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public void putAll(@NonCaptured @Unmodified @Nonnull Map<? extends K, ? extends V> map) {
        super.putAll(map);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public @Capturable @Nullable V remove(@NonCaptured @Unmodified @Nullable Object object) {
        return super.remove(object);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public void clear() {
        super.clear();
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return entrySet().map(entry -> entry == null ? "null" : entry.getKey() + ": " + entry.getValue()).join(Brackets.CURLY);
    }
    
}
