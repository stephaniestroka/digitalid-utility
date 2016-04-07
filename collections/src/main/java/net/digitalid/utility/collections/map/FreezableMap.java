package net.digitalid.utility.collections.map;

import java.util.Map;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.collections.readonly.ReadOnlyMap;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This interface models a {@link Map map} that can be {@link FreezableInterface frozen}.
 * As a consequence, all modifying methods may fail with an {@link AssertionError}.
 * <p>
 * Please note that {@link Map#entrySet()} is not supported because every entry would need
 * to be replaced with a freezable entry and such a set can no longer be backed.
 * <p>
 * <em>Important:</em> Only use immutable types for the keys and freezable or immutable types for the values!
 * (The types are not restricted to {@link FreezableInterface} or {@link Immutable} so that library types can also be used.)
 */
public interface FreezableMap<K,V> extends ReadOnlyMap<K,V>, Map<K,V>, FreezableInterface {
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    @Override
    public @Nonnull @Frozen ReadOnlyMap<K,V> freeze();
    
    /* -------------------------------------------------- Map -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull FreezableSet<K> keySet();
    
    @Pure
    @Override
    public @Nonnull FreezableCollection<V> values();
    
    /**
     * <em>Important:</em> Never call {@code Map.Entry#setValue(java.lang.Object)} on the elements!
     */
    @Pure
    @Override
    public @Nonnull FreezableSet<Map.Entry<K,V>> entrySet();
    
    /**
     * Associates the given value with the given key, if the
     * given key is not already associated with a value or null.
     * 
     * @param key the key to be associated with the given value.
     * @param value the value to be associated with the given key.
     * 
     * @return the value that is now associated with the given key.
     */
    @NonFrozenRecipient
    public @Nonnull V putIfAbsentOrNullElseReturnPresent(@Nonnull K key, @Nonnull V value);
    
}
