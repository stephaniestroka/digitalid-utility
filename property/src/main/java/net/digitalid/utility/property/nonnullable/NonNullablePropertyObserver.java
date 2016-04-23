package net.digitalid.utility.property.nonnullable;

import javax.annotation.Nonnull;

import net.digitalid.utility.generator.annotations.GenerateNoBuilder;
import net.digitalid.utility.generator.annotations.GenerateNoSubclass;
import net.digitalid.utility.property.PropertyObserver;
import net.digitalid.utility.property.ReadOnlyProperty;
import net.digitalid.utility.property.Validated;

/**
 * Objects that implement this interface can be used to observe {@link ReadOnlyNonNullableProperty non-nullable properties}.
 */
@GenerateNoBuilder
@GenerateNoSubclass
public interface NonNullablePropertyObserver<V> extends PropertyObserver {
    
    /**
     * This method is called on {@link ReadOnlyProperty#register(PropertyObserver) registered} observers when the value of the given property has been replaced.
     * 
     * @param property the property whose value has been replaced.
     * @param oldValue the old value of the given property.
     * @param newValue the new value of the given property.
     * 
     * Require.that(!newValue.equals(oldValue)).orThrow("The new value is not the same as the old value.");
     */
    public void replaced(@Nonnull ReadOnlyNonNullableProperty<V> property, @Nonnull @Validated V oldValue, @Nonnull @Validated V newValue);
    
}
