package net.digitalid.utility.property.value;

import java.util.Objects;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
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
public abstract class WritableValuePropertyImplementation<@Specifiable VALUE, @Unspecifiable EXCEPTION1 extends Exception, @Unspecifiable EXCEPTION2 extends Exception, @Unspecifiable OBSERVER extends ValueObserver<VALUE, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY>, @Unspecifiable PROPERTY extends ReadOnlyValueProperty<VALUE, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY>> extends ReadOnlyValuePropertyImplementation<VALUE, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY> implements WritableValueProperty<VALUE, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY> {
    
    /* -------------------------------------------------- Notification -------------------------------------------------- */
    
    /**
     * Notifies the registered observers that the value of this property has been replaced.
     * 
     * @require !Objects.equals(newValue, oldValue) : "The new value may not be the same as the old value.";
     * @require Objects.equals(newValue, get()) : "The new value has to be set for this property.";
     */
    @Impure
    @SuppressWarnings("unchecked")
    protected void notifyObservers(@NonCaptured @Unmodified @Valid VALUE oldValue, @NonCaptured @Unmodified @Valid VALUE newValue) throws EXCEPTION1, EXCEPTION2 {
        Require.that(!Objects.equals(newValue, oldValue)).orThrow("The new value $ may not be the same as the old value $.", newValue, oldValue);
        Require.that(Objects.equals(newValue, get())).orThrow("The new value $ has to be set for this property but the value was $.", newValue, get());
        
        if (!observers.isEmpty()) {
            for (@Nonnull ValueObserver<VALUE, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY> observer : observers.values()) {
                observer.notify((PROPERTY) this, oldValue, newValue);
            }
        }
    }
    
}
