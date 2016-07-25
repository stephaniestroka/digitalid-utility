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
import net.digitalid.utility.circumfixes.Brackets;
import net.digitalid.utility.collections.collection.BackedFreezableCollection;
import net.digitalid.utility.collections.collection.FreezableCollection;
import net.digitalid.utility.collections.set.BackedFreezableSet;
import net.digitalid.utility.collections.set.FreezableSet;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.immutable.entry.ReadOnlyEntrySet;
import net.digitalid.utility.validation.annotations.generation.Default;
import net.digitalid.utility.validation.annotations.generation.Recover;
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
// TODO: @GenerateBuilder
@GenerateSubclass
@Freezable(ReadOnlyMap.class)
public abstract class FreezableHashMap<K, V> extends HashMap<K, V> implements FreezableMap<K, V> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    @Recover
    protected FreezableHashMap(@NonNegative @Default("16") int initialCapacity, @Positive @Default("0.75f") float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    /**
     * Returns a new freezable hash map with the given initial capacity and load factor.
     */
    @Pure
    @Deprecated // TODO: Remove this method once the builder can be generated.
    public static @Capturable <K, V> @Nonnull @NonFrozen FreezableHashMap<K, V> withInitialCapacityAndLoadFactor(@NonNegative int initialCapacity, @Positive float loadFactor) {
        return new FreezableHashMapSubclass<>(initialCapacity, loadFactor);
    }
    
    /**
     * Returns a new freezable hash map with the given initial capacity.
     */
    @Pure
    @Deprecated // TODO: Remove this method once the builder can be generated.
    public static @Capturable <K, V> @Nonnull @NonFrozen FreezableHashMap<K, V> withInitialCapacity(@NonNegative int initialCapacity) {
        return FreezableHashMap.withInitialCapacityAndLoadFactor(initialCapacity, 0.75f);
    }
    
    /**
     * Returns a new freezable hash map with the default capacity.
     */
    @Pure
    @Deprecated // TODO: Remove this method once the builder can be generated.
    public static @Capturable <K, V> @Nonnull @NonFrozen FreezableHashMap<K, V> withDefaultCapacity() {
        return withInitialCapacity(16);
    }
    
    protected FreezableHashMap(@NonCaptured @Unmodified @Nonnull Map<? extends K, ? extends V> map) {
        super(map);
    }
    
    /**
     * Returns a new freezable hash map with the mappings of the given map or null if the given map is null.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public static @Capturable <K, V> @NonFrozen FreezableHashMap<K, V> withMappingsOf(ReadOnlyMap<? extends K, ? extends V> map) {
        return map == null ? null : new FreezableHashMapSubclass<>((Map<? extends K, ? extends V>) map);
    }
    
    /**
     * Returns a new freezable hash map with the mappings of the given map or null if the given map is null.
     */
    @Pure
    public static @Capturable <K, V> @NonFrozen FreezableHashMap<K, V> withMappingsOf(@NonCaptured @Unmodified Map<? extends K, ? extends V> map) {
        return map == null ? null : new FreezableHashMapSubclass<>(map);
    }
    
    /**
     * Returns a new freezable hash map with the mappings of the given map or null if the given map is null.
     */
    @Pure
    public static @Capturable <K, V> @NonFrozen FreezableHashMap<K, V> withMappingsOf(@NonCaptured @Unmodified FreezableMap<? extends K, ? extends V> map) {
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
    @NonFrozenRecipient
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
