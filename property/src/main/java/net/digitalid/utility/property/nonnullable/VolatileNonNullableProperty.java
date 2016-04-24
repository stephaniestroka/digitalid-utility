package net.digitalid.utility.property.nonnullable;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.property.Validated;
import net.digitalid.utility.property.ValueValidator;

/**
 * This property stores a replaceable value that cannot be null.
 */
public final class VolatileNonNullableProperty<V> extends WritableNonNullableProperty<V> {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Stores the value of this property.
     */
    private @Nonnull @Validated V value;
    
    @Pure
    @Override
    public @Nonnull @Validated V get() {
        return value;
    }
    
    @Override
    public void set(@Nonnull @Validated V newValue) {
        Require.that(getValueValidator().isValid(newValue)).orThrow("The new value is valid.");

        final @Nonnull V oldValue = this.value;
        this.value = newValue;
        
        if (!newValue.equals(oldValue)) { notify(oldValue, newValue); }
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new non-nullable replaceable property with the given initial value.
     * 
     * @param validator the validator used to validate the value of this property.
     * @param value the initial value of the new non-nullable replaceable property.
     */
    private VolatileNonNullableProperty(@Nonnull ValueValidator<? super V> validator, @Nonnull @Validated V value) {
        super(validator);

        Require.that(validator.isValid(value)).orThrow("The given value is valid.");

        this.value = value;
    }

    /**
     * Returns a new non-nullable replaceable property with the given initial value.
     * 
     * @param validator the validator used to validate the value of the new property.
     * @param value the initial value of the new non-nullable replaceable property.
     * 
     * @return a new non-nullable replaceable property with the given initial value.
     */
    @Pure
    public static @Nonnull <V> VolatileNonNullableProperty<V> get(@Nonnull ValueValidator<? super V> validator, @Nonnull @Validated V value) {
        return new VolatileNonNullableProperty<>(validator, value);
    }

    /**
     * Returns a new non-nullable replaceable property with the given initial value.
     *
     * @param value the initial value of the new non-nullable replaceable property.
     *
     * @return a new non-nullable replaceable property with the given initial value.
     */
    @Pure
    public static @Nonnull <V> VolatileNonNullableProperty<V> get(@Nonnull V value) {
        return new VolatileNonNullableProperty<>(ValueValidator.DEFAULT, value);
    }
    
}
