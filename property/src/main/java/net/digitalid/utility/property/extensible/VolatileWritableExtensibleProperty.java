package net.digitalid.utility.property.extensible;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.CallSuper;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.collections.set.FreezableSet;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.generation.Default;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Validated;

/**
 * This writable property stores an extensible set of values in volatile memory.
 * 
 * <em>Important:</em> Make sure that {@code F} is a sub-type of {@code R}!
 * Unfortunately, this cannot be enforced with the limited Java generics.
 * 
 * @invariant !get().containsNull() : "None of the values may be null.";
 * @invariant get().matchAll(getValidator()) : "Each value has to be valid.";
 */
@Mutable
@GenerateBuilder
@GenerateSubclass
public abstract class VolatileWritableExtensibleProperty<V, R extends ReadOnlySet<@Nonnull V>, F extends FreezableSet<@Nonnull V>> extends WritableExtensibleProperty<V, R> {
    
    /* -------------------------------------------------- Set -------------------------------------------------- */
    
    @Pure
    @Default("(F) net.digitalid.utility.collections.set.FreezableLinkedHashSet.withDefaultCapacity()")
    protected abstract @Nonnull @NonFrozen F getSet();
    
    @Pure
    @Override
    @SuppressWarnings("unchecked")
    public @NonCapturable @Nonnull @NonFrozen R get() {
        return (R) getSet();
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    @Impure
    @Override
    public boolean add(@Captured @Nonnull @Validated V value) {
        final boolean notAlreadyContained = getSet().add(value);
        if (notAlreadyContained) { notifyAdded(value); }
        return notAlreadyContained;
    }
    
    @Impure
    @Override
    public boolean remove(@NonCaptured @Unmodified @Nonnull @Validated V value) {
        final boolean contained = getSet().remove(value);
        if (contained) { notifyRemoved(value); }
        return contained;
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
