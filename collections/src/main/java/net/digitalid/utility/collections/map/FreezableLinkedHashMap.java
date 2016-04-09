package net.digitalid.utility.collections.map;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
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
    
    /**
     * @see LinkedHashMap#LinkedHashMap(int, float, boolean)
     */
    protected FreezableLinkedHashMap(@NonNegative int initialCapacity, @Positive float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }

    /**
     * @see LinkedHashMap#LinkedHashMap(int, float, boolean)
     */
    @Pure
    public static @Capturable @Nonnull @NonFrozen <K, V> FreezableLinkedHashMap<K, V> get(@NonNegative int initialCapacity, @Positive float loadFactor, boolean accessOrder) {
        return new FreezableLinkedHashMap<>(initialCapacity, loadFactor, accessOrder);
    }

    /**
     * @see LinkedHashMap#LinkedHashMap(int, float)
     */
    @Pure
    public static @Capturable @Nonnull @NonFrozen <K, V> FreezableLinkedHashMap<K, V> get(@NonNegative int initialCapacity, @Positive float loadFactor) {
        return get(initialCapacity, loadFactor, false);
    }
    
    /**
     * @see LinkedHashMap#LinkedHashMap(int)
     */
    @Pure
    public static @Capturable @Nonnull @NonFrozen <K, V> FreezableLinkedHashMap<K, V> get(@NonNegative int initialCapacity) {
        return get(initialCapacity, 0.75f);
    }
    
    /**
     * @see LinkedHashMap#LinkedHashMap()
     */
    @Pure
    public static @Capturable @Nonnull @NonFrozen <K, V> FreezableLinkedHashMap<K, V> get() {
        return get(16);
    }
    
    /**
     * @see LinkedHashMap#LinkedHashMap(java.util.Map)
     */
    protected FreezableLinkedHashMap(@Nonnull Map<? extends K, ? extends V> map) {
        super(map);
    }
    
    /**
     * @see LinkedHashMap#LinkedHashMap(java.util.Map)
     */
    @Pure
    public static @Capturable @Nonnull @NonFrozen <K, V> FreezableLinkedHashMap<K, V> getNonNullable(@Nonnull Map<? extends K, ? extends V> map) {
        return new FreezableLinkedHashMap<>(map);
    }
    
    /**
     * @see LinkedHashMap#LinkedHashMap(java.util.Map)
     */
    @Pure
    public static @Capturable @Nullable @NonFrozen <K, V> FreezableLinkedHashMap<K, V> getNullable(@Nullable Map<? extends K, ? extends V> map) {
        return map == null ? null : getNonNullable(map);
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
