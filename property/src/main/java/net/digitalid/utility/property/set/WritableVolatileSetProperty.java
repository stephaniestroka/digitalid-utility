package net.digitalid.utility.property.set;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.CallSuper;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.collections.set.FreezableSet;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.contracts.Validate;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.lock.LockNotHeldByCurrentThread;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This writable property stores a set of values in volatile memory.
 * 
 * <em>Important:</em> Make sure that {@code FREEZABLE_SET} is a sub-type of {@code READONLY_SET}!
 * Unfortunately, this cannot be enforced with the limited Java generics.
 * 
 * @invariant !get().containsNull() : "None of the values may be null.";
 * @invariant get().matchAll(getValidator()) : "Each value has to be valid.";
 * 
 * @see WritableVolatileSimpleSetProperty
 */
@ThreadSafe
@GenerateBuilder
@GenerateSubclass
@Mutable(ReadOnlyVolatileSetProperty.class)
public abstract class WritableVolatileSetProperty<VALUE, READONLY_SET extends ReadOnlySet<@Nonnull @Valid VALUE>, FREEZABLE_SET extends FreezableSet<@Nonnull @Valid VALUE>> extends WritableSetPropertyImplementation<VALUE, READONLY_SET, RuntimeException, VolatileSetObserver<VALUE, READONLY_SET>, ReadOnlyVolatileSetProperty<VALUE, READONLY_SET>> implements ReadOnlyVolatileSetProperty<VALUE, READONLY_SET> {
    
    /* -------------------------------------------------- Set -------------------------------------------------- */
    
    @Pure
    protected abstract @Nonnull @NonFrozen FREEZABLE_SET getSet();
    
    @Pure
    @Override
    @SuppressWarnings("unchecked")
    public @Nonnull @NonFrozen READONLY_SET get() {
        return (READONLY_SET) getSet();
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    @Impure
    @Override
    @LockNotHeldByCurrentThread
    public boolean add(@Captured @Nonnull @Valid VALUE value) {
        lock.lock();
        try {
            final boolean notAlreadyContained = getSet().add(value);
            if (notAlreadyContained) { notifyObservers(value, true); }
            return notAlreadyContained;
        } finally {
            lock.unlock();
        }
    }
    
    @Impure
    @Override
    @LockNotHeldByCurrentThread
    public boolean remove(@NonCaptured @Unmodified @Nonnull @Valid VALUE value) {
        lock.lock();
        try {
            final boolean contained = getSet().remove(value);
            if (contained) { notifyObservers(value, false); }
            return contained;
        } finally {
            lock.unlock();
        }
    }
    
    /* -------------------------------------------------- Validate -------------------------------------------------- */
    
    @Pure
    @Override
    @CallSuper
    public void validate() {
        super.validate();
        Validate.that(!get().containsNull()).orThrow("None of the values may be null.");
        Validate.that(get().matchAll(getValueValidator())).orThrow("Each value has to be valid.");
    }
    
}
