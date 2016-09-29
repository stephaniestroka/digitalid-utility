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
import net.digitalid.utility.concurrency.exceptions.ReentranceException;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This writable property stores a set of values in volatile memory.
 * 
 * <em>Important:</em> Make sure that {@code F} is a sub-type of {@code R}!
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
public abstract class WritableVolatileSetProperty<V, R extends ReadOnlySet<@Nonnull @Valid V>, F extends FreezableSet<@Nonnull @Valid V>> extends WritableSetPropertyImplementation<V, R, RuntimeException, ReadOnlyVolatileSetProperty.Observer<V, R>, ReadOnlyVolatileSetProperty<V, R>> implements ReadOnlyVolatileSetProperty<V, R> {
    
    /* -------------------------------------------------- Set -------------------------------------------------- */
    
    @Pure
    protected abstract @Nonnull @NonFrozen F getSet();
    
    @Pure
    @Override
    @SuppressWarnings("unchecked")
    public @Nonnull @NonFrozen R get() {
        return (R) getSet();
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    @Impure
    @Override
    public synchronized boolean add(@Captured @Nonnull @Valid V value) throws ReentranceException {
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
    public synchronized boolean remove(@NonCaptured @Unmodified @Nonnull @Valid V value) throws ReentranceException {
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
        Require.that(!get().containsNull()).orThrow("None of the values may be null.");
        Require.that(get().matchAll(getValueValidator())).orThrow("Each value has to be valid.");
    }
    
}
