package net.digitalid.utility.property.simple;

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
 * This read-only property stores a simple value.
 * 
 * @see WritableSimpleProperty
 */
@Mutable
public abstract class SimpleProperty<V> extends Property<V, SimpleProperty.Observer<V>> {
    
    /* -------------------------------------------------- Observer -------------------------------------------------- */
    
    /**
     * Objects that implement this interface can be used to observe {@link SimpleProperty simple properties}.
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
        public void replaced(@NonCaptured @Unmodified @Nonnull SimpleProperty<V> property, @NonCaptured @Unmodified @Valid V oldValue, @NonCaptured @Unmodified @Valid V newValue);
        
    }
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    /**
     * Returns the value of this simple property.
     */
    @Pure
    public abstract @Valid V get();
    
}
