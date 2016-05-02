package net.digitalid.utility.property.indexed;

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
import net.digitalid.utility.collections.map.FreezableMap;
import net.digitalid.utility.collections.map.ReadOnlyMap;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.getter.Default;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Validated;

/**
 * This writable property stores indexed values in volatile memory.
 * 
 * <em>Important:</em> Make sure that {@code F} is a sub-type of {@code R}!
 * Unfortunately, this cannot be enforced with the limited Java generics.
 * 
 * @invariant !getMap().keySet().containsNull() : "None of the keys may be null.";
 * @invariant !getMap().values().containsNull() : "None of the values may be null.";
 * @invariant getMap().keySet().matchAll(getKeyValidator()) : "Each key has to be valid.";
 * @invariant getMap().values().matchAll(getValueValidator()) : "Each value has to be valid.";
 */
@Mutable
@GenerateBuilder
@GenerateSubclass
public abstract class VolatileWritableIndexedProperty<K, V, R extends ReadOnlyMap<@Nonnull K, @Nonnull V>, F extends FreezableMap<@Nonnull K, @Nonnull V>> extends WritableIndexedProperty<K, V, R> {
    
    /* -------------------------------------------------- Map -------------------------------------------------- */
    
    @Pure
    @Default("(F) net.digitalid.utility.collections.map.FreezableLinkedHashMap.withDefaultCapacity()")
    protected abstract @Nonnull @NonFrozen F getMap();
    
    @Pure
    @Override
    @SuppressWarnings("unchecked")
    public @NonCapturable @Nonnull @NonFrozen R getAll() {
        return (R) getMap();
    }
    
    /* -------------------------------------------------- Values -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonCapturable @Nullable @Validated V get(@NonCaptured @Unmodified @Nonnull K key) {
        Require.that(getKeyValidator().evaluate(key)).orThrow("The key $ has to be valid.", key);
        
        return getMap().get(key);
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    @Impure
    @Override
    public void add(@Captured @Nonnull K key, @Captured @Nonnull @Validated V value) {
        Require.that(getKeyValidator().evaluate(key)).orThrow("The key $ has to be valid.", key);
        Require.that(!getAll().containsKey(key)).orThrow("The key $ may not already be used.", key);
        
        getMap().put(key, value);
        notifyAdded(key, value);
    }
    
    @Impure
    @Override
    public @Capturable @Nonnull @Validated V remove(@NonCaptured @Unmodified @Nonnull K key) {
        Require.that(getKeyValidator().evaluate(key)).orThrow("The key $ has to be valid.", key);
        Require.that(getAll().containsKey(key)).orThrow("The key $ has to be used.", key);
        
        final @Nonnull V value = getMap().remove(key);
        notifyRemoved(key, value);
        return value;
    }
    
    /* -------------------------------------------------- Validate -------------------------------------------------- */
    
    @Pure
    @Override
    @CallSuper
    public void validate() {
        super.validate();
        Require.that(!getAll().keySet().containsNull()).orThrow("None of the keys may be null.");
        Require.that(!getAll().values().containsNull()).orThrow("None of the values may be null.");
        Require.that(getAll().keySet().matchAll(getKeyValidator())).orThrow("Each key has to be valid.");
        Require.that(getAll().values().matchAll(getValueValidator())).orThrow("Each value has to be valid.");
    }
    
}
