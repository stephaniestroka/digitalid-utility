package net.digitalid.utility.property.value;

import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This read-only property stores a value in volatile memory.
 * 
 * @see WritableVolatileValueProperty
 */
@ThreadSafe
@ReadOnly(WritableVolatileValueProperty.class)
public interface ReadOnlyVolatileValueProperty<VALUE> extends ReadOnlyValueProperty<VALUE, RuntimeException, VolatileValueObserver<VALUE>, ReadOnlyVolatileValueProperty<VALUE>> {}
