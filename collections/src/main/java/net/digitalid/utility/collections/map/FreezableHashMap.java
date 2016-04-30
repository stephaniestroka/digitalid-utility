package net.digitalid.utility.collections.map;

import java.util.HashMap;
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
import net.digitalid.utility.functional.fixes.Brackets;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.immutable.entry.ReadOnlyEntrySet;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This class extends the {@link HashMap} and makes it {@link FreezableInterface freezable}.
 * It is recommended to use only {@link Immutable} types for the keys
 * and {@link ReadOnly} or {@link Immutable} types for the values.
 */
@GenerateSubclass
@Freezable(ReadOnlyMap.class)
public abstract class FreezableHashMap<K, V> extends HashMap<K, V> implements FreezableMap<K, V> {
    
    /* -------------------------------------------------- Fix this issue! -------------------------------------------------- */
    
    @Pure
    @Override
    @TODO(task = "For some reasons, the SubclassGenerator treats this method as abstract and generates a required field for it if this method is removed.", date = "2016-04-29", author = Author.KASPAR_ETTER, assignee = Author.STEPHANIE_STROKA, priority = Priority.HIGH)
    public boolean isEmpty() {
        return super.isEmpty();
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected FreezableHashMap(@NonNegative int initialCapacity, @Positive float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    /**
     * Returns a new freezable hash map with the given initial capacity and load factor.
     */
    @Pure
    public static @Capturable <K, V> @Nonnull @NonFrozen FreezableHashMap<K, V> withCapacityAndFactor(@NonNegative int initialCapacity, @Positive float loadFactor) {
        return new FreezableHashMapSubclass<>(initialCapacity, loadFactor);
    }
    
    /**
     * Returns a new freezable hash map with the given initial capacity.
     */
    @Pure
    public static @Capturable <K, V> @Nonnull @NonFrozen FreezableHashMap<K, V> withCapacity(@NonNegative int initialCapacity) {
        return FreezableHashMap.withCapacityAndFactor(initialCapacity, 0.75f);
    }
    
    /**
     * Returns a new freezable hash map with the default capacity.
     */
    @Pure
    public static @Capturable <K, V> @Nonnull @NonFrozen FreezableHashMap<K, V> withDefaultCapacity() {
        return withCapacity(16);
    }
    
    protected FreezableHashMap(@NonCaptured @Unmodified @Nonnull Map<? extends K, ? extends V> map) {
        super(map);
    }
    
    /**
     * Returns a new freezable hash map with the mappings of the given map or null if the given map is null.
     */
    @Pure
    public static @Capturable <K, V> @NonFrozen FreezableHashMap<K, V> withMappingsOf(@NonCaptured @Unmodified Map<? extends K, ? extends V> map) {
        return map == null ? null : new FreezableHashMapSubclass<>(map);
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
    public @Chainable @Nonnull @Frozen ReadOnlyMap<K,V> freeze() {
        this.frozen = true;
        return this;
    }
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableHashMap<K,V> clone() {
        return new FreezableHashMapSubclass<>(this);
    }
    
    /* -------------------------------------------------- Entries -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonCapturable @Nonnull FreezableSet<K> keySet() {
        return BackedFreezableSet.with(this, super.keySet());
    }
    
    @Pure
    @Override
    public @NonCapturable @Nonnull FreezableCollection<V> values() {
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
    public @NonCapturable @Nonnull V putIfAbsentOrNullElseReturnPresent(@Captured K key, @Captured @Nonnull V value) {
        final @Nullable V oldValue = get(key);
        if (oldValue != null) { return oldValue; }
        put(key, value);
        return value;
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
