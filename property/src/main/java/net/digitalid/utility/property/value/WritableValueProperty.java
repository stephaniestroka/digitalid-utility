package net.digitalid.utility.property.value;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.validation.annotations.lock.LockNotHeldByCurrentThread;
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
public interface WritableValueProperty<@Specifiable VALUE, @Unspecifiable EXCEPTION1 extends Exception, @Unspecifiable EXCEPTION2 extends Exception, @Unspecifiable OBSERVER extends ValueObserver<VALUE, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY>, @Unspecifiable PROPERTY extends ReadOnlyValueProperty<VALUE, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY>> extends ReadOnlyValueProperty<VALUE, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY> {
    
    /* -------------------------------------------------- Setter -------------------------------------------------- */
    
    /**
     * Sets the value of this property to the given value.
     * 
     * @return the old value of this property that got replaced by the given value.
     * 
     * @throws ReentranceException if this method is called by an observer of this property.
     */
    @Impure
    @LockNotHeldByCurrentThread
    public @Capturable @Valid VALUE set(@Captured @Valid VALUE value) throws EXCEPTION1, EXCEPTION2;
    
}
