package net.digitalid.utility.property.set;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This class implements the {@link WritableSetProperty}.
 * 
 * @see WritableVolatileSetProperty
 */
@Mutable
@ThreadSafe
public abstract class WritableSetPropertyImplementation<V, R extends ReadOnlySet<@Nonnull @Valid V>, X extends Exception, O extends ReadOnlySetProperty.Observer<V, R, X, O, P>, P extends ReadOnlySetProperty<V, R, X, O, P>> extends ReadOnlySetPropertyImplementation<V, R, X, O, P> implements WritableSetProperty<V, R, X, O, P> {
    
    /* -------------------------------------------------- Notification -------------------------------------------------- */
    
    /**
     * Notifies the registered observers that the given value has been added to or removed from this property.
     * 
     * @param added {@code true} if the given value has been added to or {@code false} if it has been removed from this property.
     * 
     * @require !added || get().contains(value) : "If the value was added, this property has to contain it now.";
     * @require added || !get().contains(value) : "If the value was removed, this property may no longer contain it.";
     */
    @Impure
    @SuppressWarnings("unchecked")
    protected void notifyObservers(@NonCaptured @Unmodified @Nonnull @Valid V value, boolean added) throws X {
        Require.that(!added || get().contains(value)).orThrow("If the value $ was added, this property has to contain it now.", value);
        Require.that(added || !get().contains(value)).orThrow("If the value $ was removed, this property may no longer contain it.", value);
        
        if (!observers.isEmpty()) {
            for (ReadOnlySetProperty.@Nonnull Observer<V, R, X, O, P> observer : observers.values()) {
                observer.notify((P) this, value, added);
            }
        }
    }
    
}
