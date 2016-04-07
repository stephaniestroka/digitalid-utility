package net.digitalid.utility.immutable;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.functional.iterables.CollectionIterable;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.functional.iterators.ReadOnlyIterableIterator;
import net.digitalid.utility.functional.iterators.ReadOnlyIterator;
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
     * Returns an immutable set with the elements of the given collection or null if the given collection is null.
     * The given collection is not captured as its elements are copied to the immutable set.
     */
    @Pure
    public static <E> ImmutableSet<E> with(@NonCaptured @Unmodified Collection<? extends E> collection) {
        return collection == null ? null : new ImmutableSet<>(collection);
    }
    
    /**
     * Returns an immutable set with the elements of the given iterable or null if the given iterable is null.
     */
    @Pure
    public static <E> ImmutableSet<E> with(FiniteIterable<? extends E> iterable) {
        return iterable == null ? null : new ImmutableSet<>(iterable);
    }
    
    /**
     * Returns an immutable set with the elements of the given array or null if the given array is null.
     * The given array is not captured as its elements are copied to the immutable set.
     */
    @Pure
    @SafeVarargs
    public static <E> ImmutableSet<E> with(@Captured E... elements) {
        return elements == null ? null : new ImmutableSet<>(Arrays.asList(elements));
    }
    
    /* -------------------------------------------------- Modified Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull ReadOnlyIterator<E> iterator() {
        return ReadOnlyIterableIterator.with(super.iterator());
    }
    
    /* -------------------------------------------------- Unsupported Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public final boolean add(@Captured E element) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final boolean addAll(@NonCaptured @Unmodified @Nonnull Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final boolean remove(@NonCaptured @Unmodified @Nullable Object object) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final boolean removeAll(@NonCaptured @Unmodified @Nonnull Collection<?> collection) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final boolean retainAll(@NonCaptured @Unmodified @Nonnull Collection<?> collection) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final void clear() {
        throw new UnsupportedOperationException();
    }
    
}
