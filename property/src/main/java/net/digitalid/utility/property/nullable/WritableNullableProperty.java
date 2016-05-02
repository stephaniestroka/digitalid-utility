package net.digitalid.utility.property.nullable;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Validated;

/**
 * This writable property stores a nullable value.
 * 
 * @see VolatileWritableNullableProperty
 */
@Mutable
public abstract class WritableNullableProperty<V> extends NullableProperty<V> {
    
    /* -------------------------------------------------- Setter -------------------------------------------------- */
    
    /**
     * Sets the value of this property to the given value.
     * 
     * @return the old value of this property that got replaced by the given value.
     */
    @Impure
    public abstract @Capturable @Nullable @Validated V set(@Captured @Nullable @Validated V value);
    
    /* -------------------------------------------------- Notification -------------------------------------------------- */
    
    /**
     * Notifies the observers that the value of this property has been replaced.
     * 
     * @require !Objects.equals(newValue, oldValue) : "The new value may not be the same as the old value.";
     * @require Objects.equals(newValue, get()) : "The new value has to be set for this property.";
     */
    @Pure
    protected void notifyObservers(@NonCaptured @Unmodified @Nullable @Validated V oldValue, @NonCaptured @Unmodified @Nullable @Validated V newValue) {
        Require.that(!Objects.equals(newValue, oldValue)).orThrow("The new value $ may not be the same as the old value $.", newValue, oldValue);
        Require.that(Objects.equals(newValue, get())).orThrow("The new value $ has to be set for this property.", newValue);
        
        if (hasObservers()) {
            for (NullableProperty.@Nonnull Observer<V> observer : getObservers()) {
                observer.replaced(this, oldValue, newValue);
            }
        }
    }
    
}
