package net.digitalid.utility.property.nullable;

import net.digitalid.utility.property.ReadOnlyProperty;
import net.digitalid.utility.validation.state.Pure;
import net.digitalid.utility.validation.state.Validated;

import javax.annotation.Nullable;

/**
 * This is the read-only abstract class for properties that stores a nullable replaceable value.
 */
public abstract class ReadOnlyNullableProperty<V> extends ReadOnlyProperty<V, NullablePropertyObserver<V>> {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new read-only nullable replaceable property.
     */
    protected ReadOnlyNullableProperty() {
        super();
    }
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    /**
     * Returns the value of this replaceable property.
     * 
     * @return the value of this replaceable property.
     */
    @Pure
    public abstract @Nullable @Validated V get();
    
}
