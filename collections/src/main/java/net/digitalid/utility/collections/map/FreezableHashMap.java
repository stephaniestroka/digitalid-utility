package net.digitalid.utility.collections.map;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCapturable;
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
import net.digitalid.utility.rootclass.ValueCollector;
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
@Freezable(ReadOnlyMap.class)
public class FreezableHashMap<K, V> extends HashMap<K, V> implements FreezableMap<K, V> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected FreezableHashMap(@NonNegative int initialCapacity, @Positive float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    /**
     * Returns a new freezable hash map with the given initial capacity and load factor.
     */
    @Pure
    public static @Capturable <K, V> @Nonnull @NonFrozen FreezableHashMap<K, V> with(@NonNegative int initialCapacity, @Positive float loadFactor) {
        return new FreezableHashMap<>(initialCapacity, loadFactor);
    }
    
    /**
     * Returns a new freezable hash map with the given initial capacity.
     */
    @Pure
    public static @Capturable <K, V> @Nonnull @NonFrozen FreezableHashMap<K, V> with(@NonNegative int initialCapacity) {
        return FreezableHashMap.with(initialCapacity, 0.75f);
    }
    
    /**
     * Returns a new freezable hash map with the default capacity.
     */
    @Pure
    public static @Capturable <K, V> @Nonnull @NonFrozen FreezableHashMap<K, V> withDefaultCapacity() {
        return with(16);
    }
    
    protected FreezableHashMap(@NonCaptured @Unmodified @Nonnull Map<? extends K, ? extends V> map) {
        super(map);
    }
    
    /**
     * Returns a new freezable hash map with the mappings of the given map or null if the given map is null.
     */
    @Pure
    public static @Capturable <K, V> @NonFrozen FreezableHashMap<K, V> with(@NonCaptured @Unmodified Map<? extends K, ? extends V> map) {
        return map == null ? null : new FreezableHashMap<>(map);
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
        return new FreezableHashMap<>(this);
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
    
    /* -------------------------------------------------- Collect Values -------------------------------------------------- */
    
    @Override
    @SuppressWarnings("unchecked")
    public void collectValues(@Nonnull ValueCollector valueCollector) {
        final Entry<K, V> firstElement = entrySet().getFirst();
        if (firstElement == null) {
            valueCollector.setNull();
        } else {
            valueCollector.setMap(this, (Class<K>) firstElement.getKey().getClass(), (Class<V>) firstElement.getKey().getClass());
        }
    }
    
}
