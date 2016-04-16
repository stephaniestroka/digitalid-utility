package net.digitalid.utility.collections.map;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.collections.collection.BackedFreezableCollection;
import net.digitalid.utility.collections.collection.FreezableCollection;
import net.digitalid.utility.collections.set.BackedFreezableSet;
import net.digitalid.utility.collections.set.FreezableSet;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.functional.fixes.Brackets;
import net.digitalid.utility.immutable.entry.ReadOnlyEntrySet;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class extends the {@link LinkedHashMap} and makes it {@link FreezableInterface freezable}.
 * It is recommended to use only {@link Immutable} types for the keys
 * and {@link ReadOnly} or {@link Immutable} types for the values.
 */
@Freezable(ReadOnlyMap.class)
public class FreezableLinkedHashMap<K, V> extends LinkedHashMap<K, V> implements FreezableMap<K, V> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected FreezableLinkedHashMap(@NonNegative int initialCapacity, @Positive float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }

    /**
     * Returns a new freezable linked hash map with the given initial capacity, load factor and access order.
     */
    @Pure
    public static <K, V> @Capturable @Nonnull @NonFrozen FreezableLinkedHashMap<K, V> with(@NonNegative int initialCapacity, @Positive float loadFactor, boolean accessOrder) {
        return new FreezableLinkedHashMap<>(initialCapacity, loadFactor, accessOrder);
    }

    /**
     * Returns a new freezable linked hash map with the given initial capacity and load factor.
     */
    @Pure
    public static <K, V> @Capturable @Nonnull @NonFrozen FreezableLinkedHashMap<K, V> with(@NonNegative int initialCapacity, @Positive float loadFactor) {
        return FreezableLinkedHashMap.with(initialCapacity, loadFactor, false);
    }
    
    /**
     * Returns a new freezable linked hash map with the given initial capacity.
     */
    @Pure
    public static <K, V> @Capturable @Nonnull @NonFrozen FreezableLinkedHashMap<K, V> with(@NonNegative int initialCapacity) {
        return FreezableLinkedHashMap.with(initialCapacity, 0.75f);
    }
    
    /**
     * Returns a new freezable linked hash map with the default capacity.
     */
    @Pure
    public static <K, V> @Capturable @Nonnull @NonFrozen FreezableLinkedHashMap<K, V> withDefaultCapacity() {
        return with(16);
    }
    
    protected FreezableLinkedHashMap(@NonCaptured @Unmodified @Nonnull Map<? extends K, ? extends V> map) {
        super(map);
    }
    
    /**
     * Returns a new freezable linked hash map with the mappings of the given map or null if the given map is null.
     */
    @Pure
    public static <K, V> @Capturable @NonFrozen FreezableLinkedHashMap<K, V> with(@NonCaptured @Unmodified Map<? extends K, ? extends V> map) {
        return map == null ? null : new FreezableLinkedHashMap<>(map);
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
    public @Chainable @Nonnull @Frozen ReadOnlyMap<K, V> freeze() {
        this.frozen = true;
        return this;
    }
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableLinkedHashMap<K,V> clone() {
        return new FreezableLinkedHashMap<>(this);
    }
    
    /* -------------------------------------------------- Entries -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull @NullableElements FreezableSet<K> keySet() {
        return BackedFreezableSet.with(this, super.keySet());
    }
    
    @Pure
    @Override
    public @Nonnull @NullableElements FreezableCollection<V> values() {
        return BackedFreezableCollection.with(this, super.values());
    }
    
    @Pure
    @Override
    public @Nonnull ReadOnlyEntrySet<K, V> entrySet() {
        return ReadOnlyEntrySet.with(super.entrySet());
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    @Impure
    @Override
    @NonFrozenRecipient
    public @Nullable V put(@Nullable K key, @Nullable V value) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.put(key, value);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public void putAll(@Nonnull Map<? extends K,? extends V> map) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        super.putAll(map);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public @Nonnull V putIfAbsentOrNullElseReturnPresent(@Nonnull K key, @Nonnull V value) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        final @Nullable V oldValue = get(key);
        if (oldValue != null) { return oldValue; }
        put(key, value);
        return value;
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public @Nullable V remove(@Nullable Object object) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.remove(object);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public void clear() {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        super.clear();
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return entrySet().map(entry -> entry == null ? "null" : entry.getKey() + ": " + entry.getValue()).join(Brackets.CURLY);
    }
    
}
