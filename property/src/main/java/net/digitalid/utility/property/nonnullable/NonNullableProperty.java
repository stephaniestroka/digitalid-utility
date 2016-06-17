package net.digitalid.utility.property.nonnullable;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.property.Property;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This read-only property stores a non-nullable value.
 * 
 * @see WritableNonNullableProperty
 */
@Mutable
public abstract class NonNullableProperty<V> extends Property<V, NonNullableProperty.Observer<V>> {
    
    /* -------------------------------------------------- Observer -------------------------------------------------- */
    
    /**
     * Objects that implement this interface can be used to observe {@link NonNullableProperty non-nullable properties}.
     */
    @Mutable
    @Functional
    public static interface Observer<V> extends Property.Observer<V> {
        
        /**
         * This method is called on {@link Property#isRegistered(net.digitalid.utility.property.Property.Observer) registered} observers when the value of the given property has been replaced.
         * 
         * @require !newValue.equals(oldValue) : "The new value may not be the same as the old value.";
         */
        @Impure
        public void replaced(@NonCaptured @Unmodified @Nonnull NonNullableProperty<V> property, @NonCaptured @Unmodified @Nonnull @Valid V oldValue, @NonCaptured @Unmodified @Nonnull @Valid V newValue);
        
    }
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    /**
     * Returns the value of this non-nullable property.
     */
    @Pure
    public abstract @Nonnull @Valid V get();
    
}
