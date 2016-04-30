package net.digitalid.utility.property.nullable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.property.Property;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.value.Validated;

/**
 * This read-only property stores a nullable value.
 * 
 * @see WritableNullableProperty
 */
@Mutable
public abstract class NullableProperty<V> extends Property<V, NullableProperty.Observer<V>> {
    
    /* -------------------------------------------------- Observer -------------------------------------------------- */
    
    /**
     * Objects that implement this interface can be used to observe {@link NullableProperty nullable properties}.
     */
    @Mutable
    @Functional
    public static interface Observer<V> extends Property.Observer<V> {
        
        /**
         * This method is called on {@link Property#isRegistered(net.digitalid.utility.property.Property.Observer) registered} observers when the value of the given property has been replaced.
         * 
         * @require !Objects.equals(newValue, oldValue) : "The new value may not be the same as the old value.";
         */
        @Impure
        public void replaced(@NonCaptured @Unmodified @Nonnull NullableProperty<V> property, @NonCaptured @Unmodified @Nullable @Validated V oldValue, @NonCaptured @Unmodified @Nullable @Validated V newValue);
        
    }
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    /**
     * Returns the value of this nullable property.
     */
    @Pure
    public abstract @NonCapturable @Nullable @Validated V get();
    
}
