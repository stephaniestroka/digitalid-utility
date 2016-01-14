package net.digitalid.utility.property.nullable;

import net.digitalid.utility.property.PropertyObserver;
import net.digitalid.utility.property.ReadOnlyProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Objects that implement this interface can be used to observe {@link ReadOnlyNullableProperty nullable properties}.
 */
public interface NullablePropertyObserver<V> extends PropertyObserver {
    
    /**
     * This method is called on {@link ReadOnlyProperty#register(PropertyObserver) registered} observers when the value of the given property has been replaced.
     * 
     * @param property the property whose value has been replaced.
     * @param oldValue the old value of the given property.
     * @param newValue the new value of the given property.
     * 
     * assert !newValue.equals(oldValue) : "The new value is not the same as the old value.";
     */
    public void replaced(@Nonnull ReadOnlyNullableProperty<V> property, @Nullable V oldValue, @Nullable V newValue);
    
}
