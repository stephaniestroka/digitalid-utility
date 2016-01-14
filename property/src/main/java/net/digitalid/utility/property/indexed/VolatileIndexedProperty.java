package net.digitalid.utility.property.indexed;

import net.digitalid.utility.collections.freezable.FreezableMap;
import net.digitalid.utility.collections.readonly.ReadOnlyCollection;
import net.digitalid.utility.collections.readonly.ReadOnlyMap;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.validation.state.Pure;
import net.digitalid.utility.validation.state.Validated;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The property stores indexed values in volatile memory.
 * 
 * <em>Important:</em> Make sure that {@code F} is a sub-type of {@code R}!
 * Unfortunately, this cannot be enforced with the limited Java generics.
 * 
 * [used for the hosts in the Server class and modules in the Service class]
 * 
 * @param <K> the type of the key that indexes the value.
 * @param <V> the type of the value.
 * @param <R> the type of the read-only map to which the map is casted to when retrieved with getMap().
 * @param <F> the type of the map that is used to store the key-value pairs.
 */
public class VolatileIndexedProperty<K, V, R extends ReadOnlyMap<K, V>, F extends FreezableMap<K, V>> extends WritableIndexedProperty<K, V, R, F> {
    
    /* -------------------------------------------------- Map -------------------------------------------------- */
    
    /**
     * Stores the indexed value of the property.
     */
    private final @Nonnull @Validated F map;
    
    @Pure
    @Override
    @SuppressWarnings("unchecked")
    public @Nonnull @Validated @NonFrozen R getMap() {
        return (R) map;
    }
    
    /* -------------------------------------------------- Values -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nullable @Validated V get(@Nonnull K key) {
        return map.get(key);
    }
    
    @Pure
    @Override
    public @Nonnull @Validated ReadOnlyCollection<V> getAll() {
        return map.values();
    }
    
    /* -------------------------------------------------- Modifiers -------------------------------------------------- */
    
    @Override
    public void add(@Nonnull K key, @Nonnull @Validated V value) {
        assert !map.containsKey(key) : "The key may not already be indexed.";
        
        map.put(key, value);
        
        notifyAdded(key, value);
    }
    
    @Override
    public void remove(@Nonnull @Validated K key) {
        V removedValue = map.remove(key);
        
        if (removedValue != null) {
            notifyRemoved(key, removedValue);
        }
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new volatile indexed property object, which is used to store indexed property values in the given map.
     * 
     * @param map the map that stores the indexed value of the property.
     * 
     */
    private VolatileIndexedProperty(@Nonnull @Validated F map) {
        this.map = map;
    }
    
    /**
     * Creates a new volatile indexed property with the given map.
     * 
     * @param map the map that stores the indexed value of the property.
     * 
     * @return a new volatile indexed property object.
     */
    @Pure
    public static @Nullable <K, V, R extends ReadOnlyMap<K, V>, F extends FreezableMap<K, V>> VolatileIndexedProperty<K, V, R, F> get(@Nonnull @Validated F map) {
        return new VolatileIndexedProperty<K, V, R, F>(map);
    }

}
