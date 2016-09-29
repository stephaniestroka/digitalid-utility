package net.digitalid.utility.property.value;

import java.util.Objects;

import net.digitalid.utility.annotations.method.CallSuper;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.concurrency.exceptions.ReentranceException;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This writable property stores a value in volatile memory.
 */
@ThreadSafe
@GenerateBuilder
@GenerateSubclass
@Mutable(ReadOnlyVolatileValueProperty.class)
public abstract class WritableVolatileValueProperty<V> extends WritableValuePropertyImplementation<V, RuntimeException, ReadOnlyVolatileValueProperty.Observer<V>, ReadOnlyVolatileValueProperty<V>> implements ReadOnlyVolatileValueProperty<V> {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    private @Valid V value;
    
    @Pure
    @Override
    public @NonCapturable @Valid V get() {
        return value;
    }
    
    @Impure
    @Override
    public @Capturable @Valid V set(@Captured @Valid V newValue) throws ReentranceException {
        lock.lock();
        try {
            final @Valid V oldValue = this.value;
            this.value = newValue;
            if (!Objects.equals(newValue, oldValue)) { notifyObservers(oldValue, newValue); }
            return oldValue;
        } finally {
            lock.unlock();
        }
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    protected WritableVolatileValueProperty(@Captured V value) {
        this.value = value;
    }
    
    @Pure
    @Override
    @CallSuper
    protected void initialize() {
        Require.that(isValid(value)).orThrow("The value has to be valid but was $.", value);
        super.initialize();
    }
    
}
