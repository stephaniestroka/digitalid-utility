package net.digitalid.utility.property.value;

import java.util.Objects;

import net.digitalid.utility.annotations.method.CallSuper;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.lock.LockNotHeldByCurrentThread;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This writable property stores a value in volatile memory.
 */
@ThreadSafe
@GenerateBuilder
@GenerateSubclass
@Mutable(ReadOnlyVolatileValueProperty.class)
public abstract class WritableVolatileValueProperty<VALUE> extends WritableValuePropertyImplementation<VALUE, RuntimeException, VolatileValueObserver<VALUE>, ReadOnlyVolatileValueProperty<VALUE>> implements ReadOnlyVolatileValueProperty<VALUE> {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    private @Valid VALUE value;
    
    @Pure
    @Override
    public @NonCapturable @Valid VALUE get() {
        return value;
    }
    
    @Impure
    @Override
    @LockNotHeldByCurrentThread
    public @Capturable @Valid VALUE set(@Captured @Valid VALUE newValue) {
        lock.lock();
        try {
            final @Valid VALUE oldValue = this.value;
            this.value = newValue;
            if (!Objects.equals(newValue, oldValue)) { notifyObservers(oldValue, newValue); }
            return oldValue;
        } finally {
            lock.unlock();
        }
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    protected WritableVolatileValueProperty(@Captured VALUE value) {
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
