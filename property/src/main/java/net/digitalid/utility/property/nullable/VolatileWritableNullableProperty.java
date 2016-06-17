package net.digitalid.utility.property.nullable;

import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This writable property stores a nullable value in volatile memory.
 */
@Mutable
@GenerateBuilder
@GenerateSubclass
public abstract class VolatileWritableNullableProperty<V> extends WritableNullableProperty<V> {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    private @Nullable @Valid V value;
    
    @Pure
    @Override
    public @NonCapturable @Nullable @Valid V get() {
        return value;
    }
    
    @Impure
    @Override
    public @Capturable @Nullable @Valid V set(@Captured @Nullable @Valid V newValue) {
        final @Nullable V oldValue = this.value;
        this.value = newValue;
        
        if (!newValue.equals(oldValue)) { notifyObservers(oldValue, newValue); }
        
        return oldValue;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected VolatileWritableNullableProperty(@Captured @Nullable @Valid V value) {
        this.value = value;
    }
    
}
