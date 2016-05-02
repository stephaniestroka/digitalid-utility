package net.digitalid.utility.property.nonnullable;

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
 * This writable property stores a non-nullable value.
 * 
 * @see VolatileWritableNonNullableProperty
 */
@Mutable
public abstract class WritableNonNullableProperty<V> extends NonNullableProperty<V> {
    
    /* -------------------------------------------------- Setter -------------------------------------------------- */
    
    /**
     * Sets the value of this property to the given value.
     * 
     * @return the old value of this property that got replaced by the given value.
     */
    @Impure
    public abstract @Capturable @Nullable @Validated V set(@Captured @Nonnull @Validated V value);
    
    /* -------------------------------------------------- Notification -------------------------------------------------- */
    
    /**
     * Notifies the observers that the value of this property has been replaced.
     * 
     * @require !newValue.equals(oldValue) : "The new value may not be the same as the old value.";
     * @require newValue.equals(get()) : "The new value has to be set for this property.";
     */
    @Pure
    protected void notifyObservers(@NonCaptured @Unmodified @Nonnull @Validated V oldValue, @NonCaptured @Unmodified @Nonnull @Validated V newValue) {
        Require.that(!newValue.equals(oldValue)).orThrow("The new value $ may not be the same as the old value $.", newValue, oldValue);
        Require.that(newValue.equals(get())).orThrow("The new value $ has to be set for this property.", newValue);
        
        if (hasObservers()) {
            for (NonNullableProperty.@Nonnull Observer<V> observer : getObservers()) {
                observer.replaced(this, oldValue, newValue);
            }
        }
    }
    
}
