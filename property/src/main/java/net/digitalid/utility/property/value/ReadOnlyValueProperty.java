package net.digitalid.utility.property.value;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.property.Property;
import net.digitalid.utility.validation.annotations.type.ReadOnly;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This read-only property stores a value.
 * 
 * @see WritableValueProperty
 * @see ReadOnlyVolatileValueProperty
 * @see ReadOnlyValuePropertyImplementation
 */
@ThreadSafe
@ReadOnly(WritableValueProperty.class)
public interface ReadOnlyValueProperty<VALUE, EXCEPTION extends Exception, OBSERVER extends ValueObserver<VALUE, EXCEPTION, OBSERVER, PROPERTY>, PROPERTY extends ReadOnlyValueProperty<VALUE, EXCEPTION, OBSERVER, PROPERTY>> extends Property<OBSERVER>, Valid.Value<VALUE> {
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    /**
     * Returns the value of this property.
     */
    @Pure
    public @NonCapturable @Valid VALUE get() throws EXCEPTION;
    
}
