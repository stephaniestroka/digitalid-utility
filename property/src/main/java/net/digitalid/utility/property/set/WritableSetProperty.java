package net.digitalid.utility.property.set;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This writable property stores a set of values.
 * 
 * @see WritableVolatileSetProperty
 * @see WritableSetPropertyImplementation
 */
@Mutable(ReadOnlySetProperty.class)
public interface WritableSetProperty<V, R extends ReadOnlySet<@Nonnull @Valid V>, X extends Exception, O extends ReadOnlySetProperty.Observer<V, R, X, O, P>, P extends ReadOnlySetProperty<V, R, X, O, P>> extends ReadOnlySetProperty<V, R, X, O, P> {
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    /**
     * Adds the given value to the values of this property.
     * 
     * @return whether the given value was not already stored.
     */
    @Impure
    public abstract boolean add(@Captured @Nonnull @Valid V value) throws X;
    
    /**
     * Removes the given value from the values of this property.
     * 
     * @return whether the given value was actually stored.
     */
    @Impure
    public abstract boolean remove(@NonCaptured @Unmodified @Nonnull @Valid V value) throws X;
    
}
