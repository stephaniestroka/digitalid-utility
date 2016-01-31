package net.digitalid.utility.property.nonnullable;

import javax.annotation.Nonnull;

import net.digitalid.utility.property.ReadOnlyProperty;
import net.digitalid.utility.property.Validated;
import net.digitalid.utility.property.ValueValidator;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This is the read-only abstract class for properties that stores a non-nullable replaceable value.
 */
public abstract class ReadOnlyNonNullableProperty<V> extends ReadOnlyProperty<V, NonNullablePropertyObserver<V>> {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new read-only non-nullable replaceable property with the given validator.
     *
     * @param validator the validator used to validate the value of this property.
     */
    protected ReadOnlyNonNullableProperty(@Nonnull ValueValidator<? super V> validator) {
        super(validator);
    }
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    /**
     * Returns the value of this replaceable property.
     * 
     * @return the value of this replaceable property.
     *
     * @ensure getValidator().isValid(return) : "The returned value is valid.";
     */
    @Pure
    public abstract @Nonnull @Validated V get();
    
}
