package net.digitalid.utility.property.value;

import java.util.Objects;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This class implements the {@link WritableValueProperty}.
 * 
 * @see WritableVolatileValueProperty
 */
@Mutable
@ThreadSafe
public abstract class WritableValuePropertyImplementation<V, X extends Exception, O extends ReadOnlyValueProperty.Observer<V, X, O, P>, P extends ReadOnlyValueProperty<V, X, O, P>> extends ReadOnlyValuePropertyImplementation<V, X, O, P> implements WritableValueProperty<V, X, O, P> {
    
    /* -------------------------------------------------- Notification -------------------------------------------------- */
    
    /**
     * Notifies the registered observers that the value of this property has been replaced.
     * 
     * @require !Objects.equals(newValue, oldValue) : "The new value may not be the same as the old value.";
     * @require Objects.equals(newValue, get()) : "The new value has to be set for this property.";
     */
    @Impure
    @SuppressWarnings("unchecked")
    protected void notifyObservers(@NonCaptured @Unmodified @Valid V oldValue, @NonCaptured @Unmodified @Valid V newValue) throws X {
        Require.that(!Objects.equals(newValue, oldValue)).orThrow("The new value $ may not be the same as the old value $.", newValue, oldValue);
        Require.that(Objects.equals(newValue, get())).orThrow("The new value $ has to be set for this property but the value was $.", newValue, get());
        
        if (!observers.isEmpty()) {
            for (ReadOnlyValueProperty.@Nonnull Observer<V, X, O, P> observer : observers.values()) {
                observer.notify((P) this, oldValue, newValue);
            }
        }
    }
    
}
