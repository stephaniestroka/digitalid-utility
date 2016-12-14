package net.digitalid.utility.property.set;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.concurrency.exceptions.ReentranceException;
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
public interface WritableSetProperty<VALUE, READONLY_SET extends ReadOnlySet<@Nonnull @Valid VALUE>, EXCEPTION extends Exception, OBSERVER extends SetObserver<VALUE, READONLY_SET, EXCEPTION, OBSERVER, PROPERTY>, PROPERTY extends ReadOnlySetProperty<VALUE, READONLY_SET, EXCEPTION, OBSERVER, PROPERTY>> extends ReadOnlySetProperty<VALUE, READONLY_SET, EXCEPTION, OBSERVER, PROPERTY> {
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    /**
     * Adds the given value to the values of this property.
     * 
     * @return whether the given value was not already stored.
     */
    @Impure
    public abstract boolean add(@Captured @Nonnull @Valid VALUE value) throws EXCEPTION, ReentranceException;
    
    /**
     * Removes the given value from the values of this property.
     * 
     * @return whether the given value was actually stored.
     */
    @Impure
    public abstract boolean remove(@NonCaptured @Unmodified @Nonnull @Valid VALUE value) throws EXCEPTION, ReentranceException;
    
}
