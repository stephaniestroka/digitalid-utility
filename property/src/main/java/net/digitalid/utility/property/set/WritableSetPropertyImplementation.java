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
public abstract class WritableSetPropertyImplementation<VALUE, READONLY_SET extends ReadOnlySet<@Nonnull @Valid VALUE>, EXCEPTION extends Exception, OBSERVER extends SetObserver<VALUE, READONLY_SET, EXCEPTION, OBSERVER, PROPERTY>, PROPERTY extends ReadOnlySetProperty<VALUE, READONLY_SET, EXCEPTION, OBSERVER, PROPERTY>> extends ReadOnlySetPropertyImplementation<VALUE, READONLY_SET, EXCEPTION, OBSERVER, PROPERTY> implements WritableSetProperty<VALUE, READONLY_SET, EXCEPTION, OBSERVER, PROPERTY> {
    
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
    protected void notifyObservers(@NonCaptured @Unmodified @Nonnull @Valid VALUE value, boolean added) throws EXCEPTION {
        Require.that(!added || get().contains(value)).orThrow("If the value $ was added, this property has to contain it now.", value);
        Require.that(added || !get().contains(value)).orThrow("If the value $ was removed, this property may no longer contain it.", value);
        
        if (!observers.isEmpty()) {
            for (@Nonnull SetObserver<VALUE, READONLY_SET, EXCEPTION, OBSERVER, PROPERTY> observer : observers.values()) {
                observer.notify((PROPERTY) this, value, added);
            }
        }
    }
    
}
