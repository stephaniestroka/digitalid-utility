package net.digitalid.utility.collections.collection;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.collections.iterable.FreezableIterable;
import net.digitalid.utility.collections.list.FreezableList;
import net.digitalid.utility.collections.set.FreezableSet;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This interface models a {@link Collection collection} that can be {@link FreezableInterface frozen}.
 * It is recommended to use only {@link Freezable} or {@link Immutable} types for the elements.
 * 
 * @see FreezableList
 * @see FreezableSet
 */
@ReadOnly(ReadOnlyCollection.class)
public interface FreezableCollection<E> extends ReadOnlyCollection<E>, Collection<E>, FreezableIterable<E> {
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    @Impure
    @Override
    public @Nonnull @Frozen ReadOnlyCollection<E> freeze();
    
    /* -------------------------------------------------- Conflicts -------------------------------------------------- */
    
    @Pure
    @Override
    public default boolean isEmpty() {
        return ReadOnlyCollection.super.isEmpty();
    }
    
    @Pure
    @Override
    public default boolean contains(@NonCaptured @Unmodified @Nullable Object object) {
        return ReadOnlyCollection.super.contains(object);
    }
    
    @Pure
    @Override
    public default boolean containsAll(@NonCaptured @Unmodified @Nonnull Collection<?> collection) {
        return ReadOnlyCollection.super.containsAll(collection);
    }
    
    @Pure
    @Override
    public default @Capturable @Nonnull Object[] toArray() {
        return ReadOnlyCollection.super.toArray();
    }
    
    @Pure
    @Override
    public default <T> @Capturable @Nonnull T[] toArray(@NonCaptured @Modified @Nonnull T[] array) {
        return ReadOnlyCollection.super.toArray(array);
    }
    
}
