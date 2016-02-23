package net.digitalid.utility.collections.freezable;

import java.util.Set;

import javax.annotation.Nonnull;

import net.digitalid.utility.collections.readonly.ReadOnlySet;
import net.digitalid.utility.freezable.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.functional.string.Brackets;
import net.digitalid.utility.functional.string.IterableConverter;
import net.digitalid.utility.validation.annotations.reference.Capturable;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This class implements a {@link Set set} that can be {@link Freezable frozen}.
 * As a consequence, all modifying methods may fail with an {@link AssertionError}.
 * The implementation is backed by an ordinary {@link Set set}. 
 * <p>
 * <em>Important:</em> Only use freezable or immutable types for the elements!
 * (The type is not restricted to {@link Freezable} or {@link Immutable} so that library types can also be used.)
 */
final class BackedFreezableSet<E> extends BackedFreezableCollection<E> implements FreezableSet<E> {
    
    /* -------------------------------------------------- Field -------------------------------------------------- */
    
    /**
     * Stores a reference to the set.
     */
    private final @Nonnull Set<E> set;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new backed freezable set.
     * 
     * @param freezable a reference to the underlying freezable.
     * @param set a reference to the underlying set.
     */
    private BackedFreezableSet(@Nonnull Freezable freezable, @Nonnull Set<E> set) {
        super(freezable, set);
        
        this.set = set;
    }
    
    /**
     * Creates a new backed freezable set.
     * 
     * @param freezable a reference to the underlying freezable.
     * @param set a reference to the underlying set.
     * 
     * @return a new backed freezable set.
     */
    @Pure
    static @Nonnull <E> BackedFreezableSet<E> get(@Nonnull Freezable freezable, @Nonnull Set<E> set) {
        return new BackedFreezableSet<>(freezable, set);
    }
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    @Override
    public @Nonnull @Frozen ReadOnlySet<E> freeze() {
        super.freeze();
        return this;
    }
    
    /* -------------------------------------------------- ReadOnlySet -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableSet<E> add(@Nonnull ReadOnlySet<E> set) {
        final @Nonnull FreezableSet<E> clone = clone();
        clone.addAll((FreezableSet<E>) set);
        return clone;
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableSet<E> subtract(@Nonnull ReadOnlySet<E> set) {
        final @Nonnull FreezableSet<E> clone = clone();
        clone.removeAll((FreezableSet<E>) set);
        return clone;
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableSet<E> intersect(@Nonnull ReadOnlySet<E> set) {
        final @Nonnull FreezableSet<E> clone = clone();
        clone.retainAll((FreezableSet<E>) set);
        return clone;
    }
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableSet<E> clone() {
        return FreezableHashSet.getNonNullable(set);
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return IterableConverter.toString(this, Brackets.CURLY);
    }
    
}
