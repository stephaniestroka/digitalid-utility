package net.digitalid.utility.property.nullable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.annotations.GenerateNoBuilder;
import net.digitalid.utility.generator.annotations.GenerateNoSubclass;
import net.digitalid.utility.property.Validated;
import net.digitalid.utility.property.ValueValidator;
import net.digitalid.utility.annotations.method.Pure;

/**
 * This property stores a replaceable value that can be null.
 */
@GenerateNoBuilder
@GenerateNoSubclass
public class VolatileNullableProperty<V> extends WritableNullableProperty<V> {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Stores the value of this property.
     */
    private @Nullable @Validated V value;
    
    @Pure
    @Override
    public @Nullable @Validated V get() {
        return value;
    }
    
    @Override
    public void set(@Nullable @Validated V newValue) {
        Require.that(getValueValidator().isValid(newValue)).orThrow("The new value is valid.");

        final @Nullable V oldValue = this.value;
        this.value = newValue;

        if (!newValue.equals(oldValue)) { notify(oldValue, newValue); }
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    protected VolatileNullableProperty(@Nonnull ValueValidator<? super V> validator, @Nullable @Validated V value) {
        super(validator);

        Require.that(validator.isValid(value)).orThrow("The given value is valid.");

        this.value = value;
    }
    
    /**
     * Returns a new nullable replaceable property with the given initial value.
     * 
     * @param validator the validator used to validate the value of the new property.
     * @param value the initial value of the new nullable replaceable property.
     * 
     * @return a new nullable replaceable property with the given initial value.
     */
    @Pure
    public static @Nullable <V> VolatileNullableProperty<V> get(@Nonnull ValueValidator<? super V> validator, @Nullable @Validated V value) {
        return new VolatileNullableProperty<>(validator, value);
    }

    /**
     * Returns a new nullable replaceable property with the given initial value.
     *
     * @param validator the validator used to validate the value of the new property.
     *
     * @return a new nullable replaceable property with the given initial value.
     */
    @Pure
    public static @Nullable <V> VolatileNullableProperty<V> get(@Nonnull ValueValidator<? super V> validator) {
        return get(validator, null);
    }

    /**
     * Returns a new nullable replaceable property with the given initial value.
     *
     * @param value the initial value of the new nullable replaceable property.
     *
     * @return a new nullable replaceable property with the given initial value.
     */
    @Pure
    public static @Nullable <V> VolatileNullableProperty<V> get(@Nullable @Validated V value) {
        return get(ValueValidator.DEFAULT, value);
    }
    
}