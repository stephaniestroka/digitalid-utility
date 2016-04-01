package net.digitalid.utility.immutable.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.functional.iterables.CollectionIterable;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.immutable.iterators.ImmutableIterator;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class implements an immutable set.
 */
@Immutable
public class ImmutableSet<E> extends LinkedHashSet<E> implements CollectionIterable<E> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ImmutableSet(@NonCaptured @Unmodified @Nonnull Iterable<? extends E> iterable) {
        for (E element : iterable) {
            super.add(element);
        }
    }
    
    /**
     * Returns an immutable set with the elements of the given collection.
     * The given collection is not captured as its elements are copied to the immutable set.
     */
    @Pure
    public static <E> @Nonnull ImmutableSet<E> with(@NonCaptured @Unmodified @Nonnull Collection<? extends E> collection) {
        return new ImmutableSet<>(collection);
    }
    
    /**
     * Returns an immutable set with the elements of the given iterable.
     */
    @Pure
    public static <E> @Nonnull ImmutableSet<E> with(@Nonnull FiniteIterable<? extends E> iterable) {
        return new ImmutableSet<>(iterable);
    }
    
    /**
     * Returns an immutable set with the elements of the given array.
     * The given array is not captured as its elements are copied to the immutable set.
     */
    @Pure
    @SafeVarargs
    public static <E> @Nonnull ImmutableSet<E> with(@Captured E... elements) {
        return new ImmutableSet<>(Arrays.asList(elements));
    }
    
    /* -------------------------------------------------- Modified Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull ImmutableIterator<E> iterator() {
        return ImmutableIterator.with(super.iterator());
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
    
    /* -------------------------------------------------- Conflicting Operations -------------------------------------------------- */
    
    @Pure
    @Override
    @SuppressWarnings("unchecked")
    public @Capturable @Nonnull E[] toArray() {
        return (E[]) super.toArray();
    }
    
}
