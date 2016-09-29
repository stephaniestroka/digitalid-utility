package net.digitalid.utility.property.map;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.collections.map.ReadOnlyMap;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This class implements the {@link WritableMapProperty}.
 * 
 * @see WritableVolatileMapProperty
 */
@Mutable
@ThreadSafe
public abstract class WritableMapPropertyImplementation<K, V, R extends ReadOnlyMap<@Nonnull @Valid("key") K, @Nonnull @Valid V>, X extends Exception, O extends ReadOnlyMapProperty.Observer<K, V, R, X, O, P>, P extends ReadOnlyMapProperty<K, V, R, X, O, P>> extends ReadOnlyMapPropertyImplementation<K, V, R, X, O, P> implements WritableMapProperty<K, V, R, X, O, P> {
    
    /* -------------------------------------------------- Notification -------------------------------------------------- */
    
    /**
     * Notifies the registered observers that the given key-value pair has been added to or removed from this property.
     * 
     * @require value.equals(get(key)) : "The key now has to map to the value.";
     */
    @Impure
    @SuppressWarnings("unchecked")
    protected void notifyObservers(@NonCaptured @Unmodified @Nonnull @Valid("key") K key, @NonCaptured @Unmodified @Nonnull @Valid V value, boolean added) throws X {
        Require.that(!added || value.equals(get(key))).orThrow("If the pair was added, The key $ now has to map to the value $ but mapped to $ instead.", key, value, get(key));
        Require.that(added || get(key) == null).orThrow("If the pair was removed, the key $ may no longer map to a value but still mapped to $.", key, get(key));
        
        if (!observers.isEmpty()) {
            for (ReadOnlyMapProperty.@Nonnull Observer<K, V, R, X, O, P> observer : observers.values()) {
                observer.notify((P) this, key, value, added);
            }
        }
    }
    
}
