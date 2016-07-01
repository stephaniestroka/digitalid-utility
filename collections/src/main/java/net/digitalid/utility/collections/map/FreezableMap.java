package net.digitalid.utility.collections.map;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.collections.collection.FreezableCollection;
import net.digitalid.utility.collections.set.FreezableSet;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.immutable.entry.ReadOnlyEntrySet;
import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This interface models a {@link Map map} that can be {@link FreezableInterface frozen}.
 * Please note that the method {@link Map#entrySet()} is only supported in a read-only mode.
 * It is recommended to use only {@link Immutable} types for the keys
 * and {@link ReadOnly} or {@link Immutable} types for the values.
 * 
 * @see FreezableHashMap
 * @see FreezableLinkedHashMap
 */
@Freezable(ReadOnlyMap.class)
public interface FreezableMap<K, V> extends ReadOnlyMap<K, V>, Map<K, V>, FreezableInterface {
    
    /* -------------------------------------------------- Conflict -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean isEmpty();
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    @Impure
    @Override
    @NonFrozenRecipient
    public @Chainable @Nonnull @Frozen ReadOnlyMap<K, V> freeze();
    
    /* -------------------------------------------------- Map -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonCapturable @Nonnull FreezableSet<K> keySet();
    
    @Pure
    @Override
    public @NonCapturable @Nonnull FreezableCollection<V> values();
    
    @Pure
    @Override
    public @NonCapturable @Nonnull ReadOnlyEntrySet<K, V> entrySet();
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    /**
     * Adds the entries of the given map to this map.
     */
    @Impure
    @NonFrozenRecipient
    public default void putAll(@Nonnull ReadOnlyMap<? extends K, ? extends V> map) {
        map.entrySet().forEach(entry -> put(entry.getKey(), entry.getValue()));
    }
    
    /**
     * Removes the given keys from this map.
     */
    @Impure
    @NonFrozenRecipient
    public default void removeAll(@Nonnull Collection<?> keys) {
        for (Object key : keys) { remove(key); }
    }
    
    /**
     * Removes the given keys from this map.
     */
    @Impure
    @NonFrozenRecipient
    public default void removeAll(@Nonnull FiniteIterable<?> keys) {
        for (Object key : keys) { remove(key); }
    }
    
    /**
     * Removes the given keys from this map.
     */
    @Impure
    @NonFrozenRecipient
    public default void removeAll(@Nonnull FreezableCollection<?> keys) {
        for (Object key : keys) { remove(key); }
    }
    
    /**
     * Removes the keys of the given map from this map.
     */
    @Impure
    @NonFrozenRecipient
    public default void removeAll(@Nonnull ReadOnlyMap<? extends K, ? extends V> map) {
        removeAll(keySet());
    }
    
    /**
     * Associates the given value with the given key, if the
     * given key is not already associated with a value or null.
     * 
     * @return the value that is now associated with the given key.
     */
    @Impure
    @NonFrozenRecipient
    public default @NonCapturable @Nonnull V putIfAbsentOrNullElseReturnPresent(@Captured K key, @Captured @Nonnull V value) {
        final @Nullable V oldValue = get(key);
        if (oldValue != null) { return oldValue; }
        put(key, value);
        return value;
    }
    
}
