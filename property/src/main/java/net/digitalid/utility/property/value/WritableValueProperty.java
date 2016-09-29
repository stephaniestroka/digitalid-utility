package net.digitalid.utility.property.value;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.concurrency.exceptions.ReentranceException;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This writable property stores a value.
 * 
 * @see WritableVolatileValueProperty
 * @see WritableValuePropertyImplementation
 */
@ThreadSafe
@Mutable(ReadOnlyValueProperty.class)
public interface WritableValueProperty<V, X extends Exception, O extends ReadOnlyValueProperty.Observer<V, X, O, P>, P extends ReadOnlyValueProperty<V, X, O, P>> extends ReadOnlyValueProperty<V, X, O, P> {
    
    /* -------------------------------------------------- Setter -------------------------------------------------- */
    
    /**
     * Sets the value of this property to the given value.
     * 
     * @return the old value of this property that got replaced by the given value.
     * 
     * @throws ReentranceException if this method is called by an observer of this property.
     */
    @Impure
    public @Capturable @Valid V set(@Captured @Valid V value) throws X, ReentranceException;
    
}
