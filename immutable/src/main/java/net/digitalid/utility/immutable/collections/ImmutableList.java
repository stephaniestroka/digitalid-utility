package net.digitalid.utility.immutable.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.functional.iterables.CollectionIterable;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.immutable.iterators.ImmutableIterator;
import net.digitalid.utility.immutable.iterators.ImmutableListIterator;
import net.digitalid.utility.validation.annotations.index.Index;
import net.digitalid.utility.validation.annotations.index.IndexForInsertion;
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
     * Returns an immutable list with the elements of the given collection in the same order.
     * The given collection is not captured as its elements are copied to the immutable list.
     */
    @Pure
    public static <E> @Nonnull ImmutableList<E> with(@NonCaptured @Unmodified @Nonnull Collection<? extends E> collection) {
        return new ImmutableList<>(collection);
    }
    
    /**
     * Returns an immutable list with the elements of the given iterable in the same order.
     */
    @Pure
    public static <E> @Nonnull ImmutableList<E> with(@Nonnull FiniteIterable<? extends E> iterable) {
        return new ImmutableList<>(iterable);
    }
    
    /**
     * Returns an immutable list with the elements of the given array in the same order.
     * The given array is not captured as its elements are copied to the immutable list.
     */
    @Pure
    @SafeVarargs
    public static <E> @Nonnull ImmutableList<E> with(@Captured E... elements) {
        return new ImmutableList<>(Arrays.asList(elements));
    }
    
    /* -------------------------------------------------- Modified Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull ImmutableIterator<E> iterator() {
        return ImmutableIterator.with(super.iterator());
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull ImmutableListIterator<E> listIterator() {
        return ImmutableListIterator.with(super.listIterator());
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull ImmutableListIterator<E> listIterator(@IndexForInsertion int index) {
        return ImmutableListIterator.with(super.listIterator(index));
    }
    
    @Pure
    @Override
    public @Nonnull ImmutableList<E> subList(@Index int fromIndex, @IndexForInsertion int toIndex) {
        // Copies the elements of this immutable list, which leads to some overhead but also prevents memory leaks.
        return ImmutableList.with(super.subList(fromIndex, toIndex));
    }
    
    /* -------------------------------------------------- Unsupported Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public final boolean add(@NonCaptured @Unmodified E e) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final boolean remove(@NonCaptured @Unmodified Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final boolean addAll(@NonCaptured @Unmodified Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final boolean removeAll(@NonCaptured @Unmodified Collection<?> c) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final boolean retainAll(@NonCaptured @Unmodified Collection<?> c) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final void clear() {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final E set(int index, @NonCaptured @Unmodified E element) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final void add(int index, @NonCaptured @Unmodified E element) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final E remove(int index) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    protected final void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final void trimToSize() {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final void ensureCapacity(int minCapacity) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final boolean addAll(int index, @NonCaptured @Unmodified Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }
    
    /* -------------------------------------------------- Conflicting Operations -------------------------------------------------- */
    
    @Pure
    @Override
    @SuppressWarnings("unchecked")
    public @Capturable @Nonnull E[] toArray() {
        return (E[]) super.toArray();
    }
    
}
