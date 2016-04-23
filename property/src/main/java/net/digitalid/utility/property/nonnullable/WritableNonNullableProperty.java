package net.digitalid.utility.property.nonnullable;

import javax.annotation.Nonnull;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.annotations.GenerateNoBuilder;
import net.digitalid.utility.generator.annotations.GenerateNoSubclass;
import net.digitalid.utility.property.Validated;
import net.digitalid.utility.property.ValueValidator;

/**
 * This is the writable abstract class for properties that stores a non-null replaceable value.
 * 
 * @see VolatileNonNullableProperty
 */
@GenerateNoBuilder
@GenerateNoSubclass
public abstract class WritableNonNullableProperty<V> extends ReadOnlyNonNullableProperty<V> {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new non-nullable replaceable property with the given validator.
     *
     * @param validator the validator used to validate the value of this property.
     */
    protected WritableNonNullableProperty(@Nonnull ValueValidator<? super V> validator) {
        super(validator);
    }
    
    /* -------------------------------------------------- Setter -------------------------------------------------- */
    
    /**
     * Sets the value of this property to the given new value.
     * 
     * @param newValue the new value to replace the old one with.
     *
     * @require getValidator().isValid(newValue) : "The new value is valid.";
     */
    public abstract void set(@Nonnull @Validated V newValue);
    
    /* -------------------------------------------------- Notification -------------------------------------------------- */
    
    /**
     * Notifies the observers that the value of this property has changed.
     * 
     * @param oldValue the old value of this property that got replaced.
     * @param newValue the new value of this property that replaced the old one.
     *
     * @require !oldValue.equals(newValue) : "The old and the new value are not the same.";
     */
    protected final void notify(@Nonnull @Validated V oldValue, @Nonnull @Validated V newValue) {
        Require.that(!oldValue.equals(newValue)).orThrow("The old and the new value are not the same.");
        
        if (hasObservers()) {
            for (final @Nonnull NonNullablePropertyObserver<V> observer : getObservers()) {
                observer.replaced(this, oldValue, newValue);
            }
        }
    }
    
}
