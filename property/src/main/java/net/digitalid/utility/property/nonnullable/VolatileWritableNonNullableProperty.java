package net.digitalid.utility.property.nonnullable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Validated;

/**
 * This writable property stores a non-nullable value in volatile memory.
 */
@Mutable
@GenerateBuilder
@GenerateSubclass
public abstract class VolatileWritableNonNullableProperty<V> extends WritableNonNullableProperty<V> {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    private @Nonnull @Validated V value;
    
    @Pure
    @Override
    public @NonCapturable @Nonnull @Validated V get() {
        return value;
    }
    
    @Impure
    @Override
    public @Capturable @Nullable @Validated V set(@Captured @Nonnull @Validated V newValue) {
        final @Nonnull V oldValue = this.value;
        this.value = newValue;
        
        if (!newValue.equals(oldValue)) { notifyObservers(oldValue, newValue); }
        
        return oldValue;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    protected VolatileWritableNonNullableProperty(@Captured @Nonnull @Validated V value) {
        this.value = value;
    }
    
}
