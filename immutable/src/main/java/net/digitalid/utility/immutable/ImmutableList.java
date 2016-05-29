package net.digitalid.utility.immutable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.state.Unmodifiable;
import net.digitalid.utility.functional.iterables.CollectionIterable;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.functional.iterators.ReadOnlyIterableIterator;
import net.digitalid.utility.functional.iterators.ReadOnlyIterator;
import net.digitalid.utility.functional.iterators.ReadOnlyListIterator;
import net.digitalid.utility.validation.annotations.index.Index;
import net.digitalid.utility.validation.annotations.index.IndexForInsertion;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class implements an immutable list.
 */
@Immutable
public class ImmutableList<E> extends ArrayList<E> implements CollectionIterable<E> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ImmutableList(@NonCaptured @Unmodified @Nonnull Iterable<? extends E> iterable) {
        for (E element : iterable) {
            super.add(element);
        }
    }
    
    /**
     * Returns an immutable list with the elements of the given collection in the same order or null if the given collection is null.
     * The given collection is not captured as its elements are copied to the immutable list.
     */
    @Pure
    public static <E> ImmutableList<E> with(@NonCaptured @Unmodified Collection<? extends E> collection) {
        return collection == null ? null : new ImmutableList<>(collection);
    }
    
    /**
     * Returns an immutable list with the elements of the given iterable in the same order or null if the given iterable is null.
     */
    @Pure
    public static <E> ImmutableList<E> with(FiniteIterable<? extends E> iterable) {
        return iterable == null ? null : new ImmutableList<>(iterable);
    }
    
    /**
     * Returns an immutable list with the elements of the given array in the same order or null if the given array is null.
     * The given array is not captured as its elements are copied to the immutable list.
     */
    @Pure
    @SafeVarargs
    public static <E> ImmutableList<E> with(@NonCaptured @Unmodified E... elements) {
        return elements == null ? null : new ImmutableList<>(Arrays.asList(elements));
    }
    
    /* -------------------------------------------------- Modified Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull ReadOnlyIterator<E> iterator() {
        return ReadOnlyIterableIterator.with(super.iterator());
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull ReadOnlyListIterator<E> listIterator() {
        return ReadOnlyListIterator.with(super.listIterator());
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull ReadOnlyListIterator<E> listIterator(@IndexForInsertion int index) {
        return ReadOnlyListIterator.with(super.listIterator(index));
    }
    
    @Pure
    @Override
    public @Unmodifiable @Nonnull List<E> subList(@Index int fromIndex, @IndexForInsertion int toIndex) {
        return Collections.unmodifiableList(super.subList(fromIndex, toIndex));
    }
    
    /* -------------------------------------------------- Add -------------------------------------------------- */
    
    @Pure
    @Override
    public final boolean add(@Captured E e) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final void add(@IndexForInsertion int index, @Captured E element) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final boolean addAll(@NonCaptured @Unmodified @Nonnull Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final boolean addAll(@IndexForInsertion int index, @NonCaptured @Unmodified @Nonnull Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }
    
    /* -------------------------------------------------- Remove -------------------------------------------------- */
    
    @Pure
    @Override
    public final E remove(@Index int index) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final boolean remove(@NonCaptured @Unmodified @Nullable Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    protected final void removeRange(@Index int fromIndex, @IndexForInsertion int toIndex) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final boolean removeAll(@NonCaptured @Unmodified @Nonnull Collection<?> c) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final boolean retainAll(@NonCaptured @Unmodified @Nonnull Collection<?> c) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final void clear() {
        throw new UnsupportedOperationException();
    }
    
    /* -------------------------------------------------- Set -------------------------------------------------- */
    
    @Pure
    @Override
    public final E set(@Index int index, @Captured E element) {
        throw new UnsupportedOperationException();
    }
    
    /* -------------------------------------------------- Capacity -------------------------------------------------- */
    
    @Pure
    @Override
    public final void ensureCapacity(@NonNegative int minCapacity) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final void trimToSize() {
        throw new UnsupportedOperationException();
    }
    
}
