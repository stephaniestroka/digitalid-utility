package net.digitalid.utility.property.set;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.validation.annotations.lock.LockNotHeldByCurrentThread;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This writable property stores a set of values.
 * 
 * @see WritableVolatileSetProperty
 * @see WritableSetPropertyImplementation
 */
@ThreadSafe
@Mutable(ReadOnlySetProperty.class)
public interface WritableSetProperty<@Unspecifiable VALUE, @Unspecifiable READONLY_SET extends ReadOnlySet<@Nonnull @Valid VALUE>, @Unspecifiable EXCEPTION1 extends Exception, @Unspecifiable EXCEPTION2 extends Exception, @Unspecifiable OBSERVER extends SetObserver<VALUE, READONLY_SET, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY>, @Unspecifiable PROPERTY extends ReadOnlySetProperty<VALUE, READONLY_SET, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY>> extends ReadOnlySetProperty<VALUE, READONLY_SET, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY> {
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    /**
     * Adds the given value to the values of this property.
     * 
     * @return whether the given value was not already stored.
     */
    @Impure
    @LockNotHeldByCurrentThread
    public abstract boolean add(@Captured @Nonnull @Valid VALUE value) throws EXCEPTION1, EXCEPTION2;
    
    /**
     * Removes the given value from the values of this property.
     * 
     * @return whether the given value was actually stored.
     */
    @Impure
    @LockNotHeldByCurrentThread
    public abstract boolean remove(@NonCaptured @Unmodified @Nonnull @Valid VALUE value) throws EXCEPTION1, EXCEPTION2;
    
}
