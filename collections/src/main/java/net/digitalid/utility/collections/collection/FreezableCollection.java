package net.digitalid.utility.collections.collection;

import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.collections.iterable.FreezableIterable;
import net.digitalid.utility.collections.iterator.FreezableIterator;
import net.digitalid.utility.collections.list.FreezableList;
import net.digitalid.utility.collections.set.FreezableSet;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This interface models a {@link Collection collection} that can be {@link FreezableInterface frozen}.
 * It is recommended to use only {@link ReadOnly} or {@link Immutable} types for the elements.
 * 
 * @see FreezableList
 * @see FreezableSet
 */
@Freezable(ReadOnlyCollection.class)
public interface FreezableCollection<E> extends ReadOnlyCollection<E>, Collection<E>, FreezableIterable<E> {
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    @Impure
    @Override
    @NonFrozenRecipient
    public @Chainable @Nonnull @Frozen ReadOnlyCollection<E> freeze();
    
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
    public default @Capturable <T> @Nonnull T[] toArray(@NonCaptured @Modified @Nonnull T[] array) {
        return ReadOnlyCollection.super.toArray(array);
    }
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    /**
     * Returns a freezable iterator over the elements in this collection.
     */
    @Pure
    public @Capturable @Nonnull FreezableIterator<E> freezableIterator();
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    /**
     * Adds the elements of the given iterable to this collection.
     * 
     * @return whether this collection changed as a result of the call.
     */
    @Impure
    @NonFrozenRecipient
    public default boolean addAll(@Nonnull FiniteIterable<? extends E> iterable) {
        boolean modified = false;
        for (E element : iterable) { modified = add(element) || modified; }
        return modified;
    }
    
    /**
     * Adds the elements of the given collection to this collection.
     * 
     * @return whether this collection changed as a result of the call.
     */
    @Impure
    @NonFrozenRecipient
    public default boolean addAll(@NonCaptured @Unmodified @Nonnull FreezableCollection<? extends E> collection) {
        return addAll((FiniteIterable<? extends E>) collection);
    }
    
    /**
     * Removes the elements of the given iterable from this collection.
     * 
     * @return whether this collection changed as a result of the call.
     */
    @Impure
    @NonFrozenRecipient
    public default boolean removeAll(@Nonnull FiniteIterable<?> iterable) {
        boolean modified = false;
        final @Nonnull Iterator<E> iterator = freezableIterator();
        while (iterator.hasNext()) {
            if (iterable.contains(iterator.next())) {
                iterator.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    /**
     * Removes the elements of the given collection from this collection.
     * 
     * @return whether this collection changed as a result of the call.
     */
    @Impure
    @Override
    @NonFrozenRecipient
    public default boolean removeAll(@NonCaptured @Unmodified @Nonnull Collection<?> collection) {
        return removeAll(FiniteIterable.of(collection));
    }
    
    /**
     * Removes the elements of the given collection from this collection.
     * 
     * @return whether this collection changed as a result of the call.
     */
    @Impure
    @NonFrozenRecipient
    public default boolean removeAll(@NonCaptured @Unmodified @Nonnull FreezableCollection<?> collection) {
        return removeAll((FiniteIterable<?>) collection);
    }
    
    /**
     * Retains the elements of the given iterable and removes all other.
     * 
     * @return whether this collection changed as a result of the call.
     */
    @Impure
    @NonFrozenRecipient
    public default boolean retainAll(@Nonnull FiniteIterable<?> iterable) {
        boolean modified = false;
        final @Nonnull Iterator<E> iterator = freezableIterator();
        while (iterator.hasNext()) {
            if (!iterable.contains(iterator.next())) {
                iterator.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    /**
     * Retains the elements of the given collection and removes all other.
     * 
     * @return whether this collection changed as a result of the call.
     */
    @Impure
    @Override
    @NonFrozenRecipient
    public default boolean retainAll(@NonCaptured @Unmodified @Nonnull Collection<?> collection) {
        return retainAll(FiniteIterable.of(collection));
    }
    
    /**
     * Retains the elements of the given collection and removes all other.
     * 
     * @return whether this collection changed as a result of the call.
     */
    @Impure
    @NonFrozenRecipient
    public default boolean retainAll(@NonCaptured @Unmodified @Nonnull FreezableCollection<?> collection) {
        return retainAll((FiniteIterable<?>) collection);
    }
    
}
