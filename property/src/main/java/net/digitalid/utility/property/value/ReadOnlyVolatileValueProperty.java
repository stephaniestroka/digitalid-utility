package net.digitalid.utility.property.value;

import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This read-only property stores a value in volatile memory.
 * 
 * @see WritableVolatileValueProperty
 */
@ReadOnly(WritableVolatileValueProperty.class)
public interface ReadOnlyVolatileValueProperty<V> extends ReadOnlyValueProperty<V, RuntimeException, ReadOnlyVolatileValueProperty.Observer<V>, ReadOnlyVolatileValueProperty<V>>{
    
    /* -------------------------------------------------- Observer -------------------------------------------------- */
    
    /**
     * Objects that implement this interface can be used to {@link #register(net.digitalid.utility.property.Property.Observer) observe} {@link ReadOnlyVolatileValueProperty read-only volatile value properties}.
     */
    @Mutable
    @Functional
    public static interface Observer<V> extends ReadOnlyValueProperty.Observer<V, RuntimeException, ReadOnlyVolatileValueProperty.Observer<V>, ReadOnlyVolatileValueProperty<V>> {}
    
}
