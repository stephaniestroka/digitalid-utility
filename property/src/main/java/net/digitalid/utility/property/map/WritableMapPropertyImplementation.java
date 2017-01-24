package net.digitalid.utility.property.map;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Unspecifiable;
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
public abstract class WritableMapPropertyImplementation<@Unspecifiable KEY, @Unspecifiable VALUE, @Unspecifiable READONLY_MAP extends ReadOnlyMap<@Nonnull @Valid("key") KEY, @Nonnull @Valid VALUE>, @Unspecifiable EXCEPTION1 extends Exception, @Unspecifiable EXCEPTION2 extends Exception, @Unspecifiable OBSERVER extends MapObserver<KEY, VALUE, READONLY_MAP, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY>, @Unspecifiable PROPERTY extends ReadOnlyMapProperty<KEY, VALUE, READONLY_MAP, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY>> extends ReadOnlyMapPropertyImplementation<KEY, VALUE, READONLY_MAP, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY> implements WritableMapProperty<KEY, VALUE, READONLY_MAP, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY> {
    
    /* -------------------------------------------------- Notification -------------------------------------------------- */
    
    /**
     * Notifies the registered observers that the given key-value pair has been added to or removed from this property.
     * 
     * @require value.equals(get(key)) : "The key now has to map to the value.";
     */
    @Impure
    @SuppressWarnings("unchecked")
    protected void notifyObservers(@NonCaptured @Unmodified @Nonnull @Valid("key") KEY key, @NonCaptured @Unmodified @Nonnull @Valid VALUE value, boolean added) throws EXCEPTION1, EXCEPTION2 {
        Require.that(!added || value.equals(get(key))).orThrow("If the pair was added, The key $ now has to map to the value $ but mapped to $ instead.", key, value, get(key));
        Require.that(added || get(key) == null).orThrow("If the pair was removed, the key $ may no longer map to a value but still mapped to $.", key, get(key));
        
        if (!observers.isEmpty()) {
            for (@Nonnull MapObserver<KEY, VALUE, READONLY_MAP, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY> observer : observers.values()) {
                observer.notify((PROPERTY) this, key, value, added);
            }
        }
    }
    
}
