package net.digitalid.utility.collections.map;

import java.util.Map;

import javax.annotation.Nonnull;

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
    
    /**
     * Associates the given value with the given key, if the
     * given key is not already associated with a value or null.
     * 
     * @return the value that is now associated with the given key.
     */
    @Impure
    @NonFrozenRecipient
    public @NonCapturable @Nonnull V putIfAbsentOrNullElseReturnPresent(@Captured K key, @Captured @Nonnull V value);
    
}
