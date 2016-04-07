package net.digitalid.utility.collections.set;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.collections.collection.FreezableCollection;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This interface models a {@link Set set} that can be {@link FreezableInterface frozen}.
 * It is recommended to use only {@link Freezable} or {@link Immutable} types for the elements.
 * 
 * @see BackedFreezableSet
 * @see FreezableHashSet
 * @see FreezableLinkedHashSet
 */
@Freezable(ReadOnlySet.class)
public interface FreezableSet<E> extends ReadOnlySet<E>, Set<E>, FreezableCollection<E> {
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    @Override
    public @Nonnull @Frozen ReadOnlySet<E> freeze();
    
    /* -------------------------------------------------- Conflicts -------------------------------------------------- */
    
    @Pure
    @Override
    public default boolean isEmpty() {
        return FreezableCollection.super.isEmpty();
    }
    
    @Pure
    @Override
    public default boolean contains(@NonCaptured @Unmodified @Nullable Object object) {
        return FreezableCollection.super.contains(object);
    }
    
    @Pure
    @Override
    public default boolean containsAll(@NonCaptured @Unmodified @Nonnull Collection<?> collection) {
        return FreezableCollection.super.containsAll(collection);
    }
    
    @Pure
    @Override
    public default @Capturable @Nonnull Object[] toArray() {
        return FreezableCollection.super.toArray();
    }
    
    @Pure
    @Override
    @SuppressWarnings("SuspiciousToArrayCall")
    public default <T> @Capturable @Nonnull T[] toArray(@NonCaptured @Modified @Nonnull T[] array) {
        return FreezableCollection.super.toArray(array);
    }
    
}
