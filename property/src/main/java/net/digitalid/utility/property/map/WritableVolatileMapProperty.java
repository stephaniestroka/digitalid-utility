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
import net.digitalid.utility.concurrency.exceptions.ReentranceException;
import net.digitalid.utility.contracts.Validate;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This writable property stores a map of key-value pairs in volatile memory.
 * 
 * <em>Important:</em> Make sure that {@code F} is a sub-type of {@code R}!
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
public abstract class WritableVolatileMapProperty<K, V, R extends ReadOnlyMap<@Nonnull @Valid("key") K, @Nonnull @Valid V>, F extends FreezableMap<@Nonnull @Valid("key") K, @Nonnull @Valid V>> extends WritableMapPropertyImplementation<K, V, R, RuntimeException, ReadOnlyVolatileMapProperty.Observer<K, V, R>, ReadOnlyVolatileMapProperty<K, V, R>> implements ReadOnlyVolatileMapProperty<K, V, R> {
    
    /* -------------------------------------------------- Map -------------------------------------------------- */
    
    @Pure
    protected abstract @Nonnull @NonFrozen F getMap();
    
    @Pure
    @Override
    @SuppressWarnings("unchecked")
    public @Nonnull @NonFrozen R get() {
        return (R) getMap();
    }
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonCapturable @Nullable @Valid V get(@NonCaptured @Unmodified @Nonnull @Valid("key") K key) {
        return getMap().get(key);
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    @Impure
    @Override
    public boolean add(@Captured @Nonnull @Valid("key") K key, @Captured @Nonnull @Valid V value) throws ReentranceException {
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
    public @Capturable @Nullable @Valid V remove(@NonCaptured @Unmodified @Nonnull @Valid("key") K key) throws ReentranceException {
        lock.lock();
        try {
            final @Nullable V value = getMap().remove(key);
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
