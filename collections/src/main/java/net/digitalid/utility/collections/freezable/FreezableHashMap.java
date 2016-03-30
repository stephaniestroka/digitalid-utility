package net.digitalid.utility.collections.freezable;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.collections.readonly.ReadOnlyMap;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.freezable.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.functional.fixes.Brackets;
import net.digitalid.utility.functional.fixes.ElementConverter;
import net.digitalid.utility.functional.fixes.IterableConverter;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.reference.Capturable;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class extends the {@link HashMap} and makes it {@link Freezable}.
 * Be careful when treating instances of this class as normal {@link Map maps} because all modifying methods may fail with an {@link AssertionError}.
 * <p>
 * <em>Important:</em> Only use {@link Immutable immutable} or {@link Freezable frozen} objects as keys!
 */
public class FreezableHashMap<K, V> extends HashMap<K, V> implements FreezableMap<K, V> {
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    /**
     * Stores whether this object is frozen.
     */
    private boolean frozen = false;
    
    @Pure
    @Override
    public final boolean isFrozen() {
        return frozen;
    }
    
    @Override
    public @Nonnull @Frozen ReadOnlyMap<K,V> freeze() {
        if (!frozen) {
            frozen = true;
            // Assuming that the keys are already immutable.
            for (final @Nullable V value : values()) {
                if (value instanceof Freezable) {
                    ((Freezable) value).freeze();
                } else {
                    break;
                }
            }
        }
        return this;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * @see HashMap#HashMap(int, float)
     */
    protected FreezableHashMap(@NonNegative int initialCapacity, @Positive float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    /**
     * @see HashMap#HashMap(int, float)
     */
    @Pure
    public static @Capturable @Nonnull @NonFrozen <K, V> FreezableHashMap<K, V> get(@NonNegative int initialCapacity, @Positive float loadFactor) {
        return new FreezableHashMap<>(initialCapacity, loadFactor);
    }
    
    /**
     * @see HashMap#HashMap(int)
     */
    @Pure
    public static @Capturable @Nonnull @NonFrozen <K, V> FreezableHashMap<K, V> get(@NonNegative int initialCapacity) {
        return get(initialCapacity, 0.75f);
    }
    
    /**
     * @see HashMap#HashMap()
     */
    @Pure
    public static @Capturable @Nonnull @NonFrozen <K, V> FreezableHashMap<K, V> get() {
        return get(16);
    }
    
    /**
     * @see HashMap#HashMap(java.util.Map)
     */
    protected FreezableHashMap(@Nonnull Map<? extends K, ? extends V> map) {
        super(map);
    }
    
    /**
     * @see HashMap#HashMap(java.util.Map)
     */
    @Pure
    public static @Capturable @Nonnull @NonFrozen <K, V> FreezableHashMap<K, V> getNonNullable(@Nonnull Map<? extends K, ? extends V> map) {
        return new FreezableHashMap<>(map);
    }
    
    /**
     * @see HashMap#HashMap(java.util.Map)
     */
    @Pure
    public static @Capturable @Nullable @NonFrozen <K, V> FreezableHashMap<K, V> getNullable(@Nullable Map<? extends K, ? extends V> map) {
        return map == null ? null : getNonNullable(map);
    }
    
    /* -------------------------------------------------- Entries -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull @NullableElements FreezableSet<K> keySet() {
        return BackedFreezableSet.get(this, super.keySet());
    }
    
    @Pure
    @Override
    public @Nonnull @NullableElements FreezableCollection<V> values() {
        return BackedFreezableCollection.get(this, super.values());
    }
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements FreezableSet<Map.Entry<K,V>> entrySet() {
        return BackedFreezableSet.get(this, super.entrySet());
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    @Override
    @NonFrozenRecipient
    public @Nullable V put(@Nullable K key, @Nullable V value) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.put(key, value);
    }
    
    @Override
    @NonFrozenRecipient
    public void putAll(@Nonnull Map<? extends K,? extends V> map) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        super.putAll(map);
    }
    
    @Override
    @NonFrozenRecipient
    public @Nonnull V putIfAbsentOrNullElseReturnPresent(@Nonnull K key, @Nonnull V value) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        final @Nullable V oldValue = get(key);
        if (oldValue != null) { return oldValue; }
        put(key, value);
        return value;
    }
    
    @Override
    @NonFrozenRecipient
    public @Nullable V remove(@Nullable Object object) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.remove(object);
    }
    
    @Override
    @NonFrozenRecipient
    public void clear() {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        super.clear();
    }
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableHashMap<K,V> clone() {
        return new FreezableHashMap<>(this);
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return IterableConverter.toString(entrySet(), new ElementConverter<Entry<K, V>>() { @Pure @Override public String toString(@Nullable Entry<K, V> entry) { return entry == null ? "null" : entry.getKey() + ": " + entry.getValue(); } }, Brackets.CURLY);
    }
    
}
