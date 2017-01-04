package net.digitalid.utility.property.map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.CallSuper;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.collections.map.FreezableMap;
import net.digitalid.utility.collections.map.ReadOnlyMap;
import net.digitalid.utility.contracts.Validate;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.lock.LockNotHeldByCurrentThread;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This writable property stores a map of key-value pairs in volatile memory.
 * 
 * <em>Important:</em> Make sure that {@code FREEZABLE_MAP} is a sub-type of {@code READONLY_MAP}!
 * Unfortunately, this cannot be enforced with the limited Java generics.
 * 
 * @invariant !get().keySet().containsNull() : "None of the keys may be null.";
 * @invariant !get().values().containsNull() : "None of the values may be null.";
 * @invariant get().keySet().matchAll(getKeyValidator()) : "Each key has to be valid.";
 * @invariant get().values().matchAll(getValueValidator()) : "Each value has to be valid.";
 */
@ThreadSafe
@GenerateBuilder
@GenerateSubclass
@Mutable(ReadOnlyVolatileMapProperty.class)
public abstract class WritableVolatileMapProperty<KEY, VALUE, READONLY_MAP extends ReadOnlyMap<@Nonnull @Valid("key") KEY, @Nonnull @Valid VALUE>, FREEZABLE_MAP extends FreezableMap<@Nonnull @Valid("key") KEY, @Nonnull @Valid VALUE>> extends WritableMapPropertyImplementation<KEY, VALUE, READONLY_MAP, RuntimeException, VolatileMapObserver<KEY, VALUE, READONLY_MAP>, ReadOnlyVolatileMapProperty<KEY, VALUE, READONLY_MAP>> implements ReadOnlyVolatileMapProperty<KEY, VALUE, READONLY_MAP> {
    
    /* -------------------------------------------------- Map -------------------------------------------------- */
    
    @Pure
    protected abstract @Nonnull @NonFrozen FREEZABLE_MAP getMap();
    
    @Pure
    @Override
    @SuppressWarnings("unchecked")
    public @Nonnull @NonFrozen READONLY_MAP get() {
        return (READONLY_MAP) getMap();
    }
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonCapturable @Nullable @Valid VALUE get(@NonCaptured @Unmodified @Nonnull @Valid("key") KEY key) {
        return getMap().get(key);
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    @Impure
    @Override
    @LockNotHeldByCurrentThread
    public boolean add(@Captured @Nonnull @Valid("key") KEY key, @Captured @Nonnull @Valid VALUE value) {
        lock.lock();
        try {
            if (getMap().containsKey(key)) {
                return false;
            } else {
                getMap().put(key, value);
                notifyObservers(key, value, true);
                return true;
            }
        } finally {
            lock.unlock();
        }
    }
    
    @Impure
    @Override
    @LockNotHeldByCurrentThread
    public @Capturable @Nullable @Valid VALUE remove(@NonCaptured @Unmodified @Nonnull @Valid("key") KEY key) {
        lock.lock();
        try {
            final @Nullable VALUE value = getMap().remove(key);
            if (value != null) { notifyObservers(key, value, false); }
            return value;
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
        Validate.that(!get().keySet().containsNull()).orThrow("None of the keys may be null.");
        Validate.that(!get().values().containsNull()).orThrow("None of the values may be null.");
        Validate.that(get().keySet().matchAll(getKeyValidator())).orThrow("Each key has to be valid.");
        Validate.that(get().values().matchAll(getValueValidator())).orThrow("Each value has to be valid.");
    }
    
}
