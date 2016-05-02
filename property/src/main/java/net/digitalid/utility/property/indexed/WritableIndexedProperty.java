package net.digitalid.utility.property.indexed;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.collections.map.ReadOnlyMap;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Validated;

/**
 * This writable property stores indexed values.
 * 
 * @see VolatileWritableIndexedProperty
 */
@Mutable
public abstract class WritableIndexedProperty<K, V, R extends ReadOnlyMap<@Nonnull K, @Nonnull V>> extends IndexedProperty<K, V, R> {
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    /**
     * Adds the given value indexed by the given key to this property.
     * 
     * @require getKeyValidator().evaluate(key) : "The key has to be valid.";
     * @require !getMap().containsKey(key) : "The key may not already be used.";
     */
    @Impure
    public abstract void add(@Captured @Nonnull K key, @Captured @Nonnull @Validated V value);
    
    /**
     * Removes the value indexed by the given key from this property.
     * 
     * @return the value that was previously associated with the given key.
     * 
     * @require getKeyValidator().evaluate(key) : "The key has to be valid.";
     * @require getMap().containsKey(key) : "The key has to be used.";
     */
    @Impure
    public abstract @Capturable @Nonnull @Validated V remove(@NonCaptured @Unmodified @Nonnull K key);
    
    /* -------------------------------------------------- Notifications -------------------------------------------------- */
    
    /**
     * Notifies the observers that a key-value pair has been added.
     * 
     * @require getKeyValidator().evaluate(key) : "The key has to be valid.";
     * @require value.equals(get(key)) : "The key now has to map to the value.";
     */
    @Pure
    protected void notifyAdded(@NonCaptured @Unmodified @Nonnull K key, @NonCaptured @Unmodified @Nonnull @Validated V value) {
        Require.that(value.equals(get(key))).orThrow("The key $ now has to map to the value $.", key, value);
        
        if (hasObservers()) {
            for (IndexedProperty.@Nonnull Observer<K, V, R> observer : getObservers()) {
                observer.added(this, key, value);
            }
        }
    }
    
    /**
     * Notifies the observers that a key-value pair has been removed.
     * 
     * @require getKeyValidator().evaluate(key) : "The key has to be valid.";
     * @require !value.equals(get(key)) : "The key may no longer map to the value.";
     */
    @Pure
    protected void notifyRemoved(@NonCaptured @Unmodified @Nonnull K key, @NonCaptured @Unmodified @Nonnull @Validated V value) {
        Require.that(!value.equals(get(key))).orThrow("The key $ may no longer map to the value $.", key, value);
        
        if (hasObservers()) {
            for (IndexedProperty.@Nonnull Observer<K, V, R> observer : getObservers()) {
                observer.removed(this, key, value);
            }
        }
    }
    
}
