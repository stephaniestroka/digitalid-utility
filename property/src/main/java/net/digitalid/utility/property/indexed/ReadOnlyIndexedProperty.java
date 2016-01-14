package net.digitalid.utility.property.indexed;

import javax.annotation.Nonnull;

import net.digitalid.utility.property.ReadOnlyProperty;
import net.digitalid.utility.validation.state.Validated;
import net.digitalid.utility.collections.readonly.ReadOnlyCollection;
import net.digitalid.utility.collections.readonly.ReadOnlyMap;

/**
 * This is the read-only abstract class for properties that stores an indexed value.
 * 
 * @see WritableIndexedProperty
 */
public abstract class ReadOnlyIndexedProperty<K, V, R extends ReadOnlyMap<K, V>> extends ReadOnlyProperty<V, IndexedPropertyObserver<K, V, R>> {
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    /**
     * Returns the value for a given key.
     * 
     * @param key the key which indexes the value.
     * 
     * @return the value for a given key.
     */
    public abstract @Nonnull @Validated V get(@Nonnull K key);
    
    /**
     * Returns all indexed values.
     * 
     * @return all indexed values.
     */
    public abstract @Nonnull @Validated ReadOnlyCollection<V> getAll();
    
    /**
     * Returns a read-only representation of the map.
     * 
     * @return a read-only representation of the map.
     */
    public abstract @Nonnull @Validated R getMap();
    
}
