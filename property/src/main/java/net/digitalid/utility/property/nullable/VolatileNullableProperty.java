package net.digitalid.utility.property.nullable;

import net.digitalid.utility.validation.state.Pure;
import net.digitalid.utility.validation.state.Validated;

import javax.annotation.Nullable;

/**
 * This property stores a replaceable value that can be null.
 */
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
        final @Nullable V oldValue = this.value;
        this.value = newValue;

        if (newValue != null && !newValue.equals(oldValue) || oldValue != null && !(oldValue.equals(newValue))) {
            notify(oldValue, newValue);
        }
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    private VolatileNullableProperty(@Nullable @Validated V value) {
        this.value = value;
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
        return new VolatileNullableProperty<>(value);
    }
    
}