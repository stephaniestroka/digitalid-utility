package net.digitalid.utility.property.nonnullable;

import net.digitalid.utility.property.ReadOnlyProperty;
import net.digitalid.utility.validation.state.Pure;
import net.digitalid.utility.validation.state.Validated;

import javax.annotation.Nonnull;

/**
 * This is the read-only abstract class for properties that stores a non-nullable replaceable value.
 */
public abstract class ReadOnlyNonNullableProperty<V> extends ReadOnlyProperty<V, NonNullablePropertyObserver<V>> {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new read-only non-nullable replaceable property.
     */
    protected ReadOnlyNonNullableProperty() {
        super();
    }
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    /**
     * Returns the value of this replaceable property.
     * 
     * @return the value of this replaceable property.
     */
    @Pure
    public abstract @Nonnull @Validated V get();
    
}
