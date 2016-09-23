package net.digitalid.utility.property.value;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.property.Property;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This read-only property stores a value.
 * 
 * @see WritableValueProperty
 * @see ReadOnlyVolatileValueProperty
 * @see ReadOnlyValuePropertyImplementation
 */
@ReadOnly(WritableValueProperty.class)
public interface ReadOnlyValueProperty<V, X extends Exception, O extends ReadOnlyValueProperty.Observer<V, X, O, P>, P extends ReadOnlyValueProperty<V, X, O, P>> extends Property<O>, Valid.Value<V> {
    
    /* -------------------------------------------------- Observer -------------------------------------------------- */
    
    /**
     * Objects that implement this interface can be used to {@link #register(net.digitalid.utility.property.Property.Observer) observe} {@link ReadOnlyValueProperty read-only value properties}.
     */
    @Mutable
    @Functional
    public static interface Observer<V, X extends Exception, O extends ReadOnlyValueProperty.Observer<V, X, O, P>, P extends ReadOnlyValueProperty<V, X, O, P>> extends Property.Observer {
        
        /**
         * This method is called on {@link Property#isRegistered(net.digitalid.utility.property.Property.Observer) registered} observers when the value of the given property has been replaced.
         * 
         * @require !Objects.equals(newValue, oldValue) : "The new value may not be the same as the old value.";
         */
        @Impure
        public void notify(@Nonnull P property, @NonCaptured @Unmodified @Valid V oldValue, @NonCaptured @Unmodified @Valid V newValue);
        
    }
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    /**
     * Returns the value of this property.
     */
    @Pure
    public @NonCapturable @Valid V get() throws X;
    
}
