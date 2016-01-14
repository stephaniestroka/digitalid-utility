package net.digitalid.utility.property.nonnullable;

import net.digitalid.utility.validation.state.Validated;

import javax.annotation.Nonnull;

/**
 * This is the writable abstract class for properties that stores a non-null replaceable value.
 * 
 * @see VolatileNonNullableProperty
 */
public abstract class WritableNonNullableProperty<V> extends ReadOnlyNonNullableProperty<V> {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new non-nullable replaceable property.
     */
    protected WritableNonNullableProperty() {
        super();
    }
    
    /* -------------------------------------------------- Setter -------------------------------------------------- */
    
    /**
     * Sets the value of this property to the given new value.
     * 
     * @param newValue the new value to replace the old one with.
     */
    public abstract void set(@Nonnull @Validated V newValue);
    
    /* -------------------------------------------------- Notification -------------------------------------------------- */
    
    /**
     * Notifies the observers that the value of this property has changed.
     * 
     * @param oldValue the old value of this property that got replaced.
     * @param newValue the new value of this property that replaced the old one.
     */
    protected final void notify(@Nonnull @Validated V oldValue, @Nonnull @Validated V newValue) {
        assert !oldValue.equals(newValue) : "The old and the new value are not the same.";
        
        if (hasObservers()) {
            for (final @Nonnull NonNullablePropertyObserver<V> observer : getObservers()) {
                observer.replaced(this, oldValue, newValue);
            }
        }
    }
    
}
