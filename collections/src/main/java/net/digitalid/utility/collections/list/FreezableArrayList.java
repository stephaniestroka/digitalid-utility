package net.digitalid.utility.collections.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.functional.iterators.ReadOnlyIterableIterator;
import net.digitalid.utility.functional.iterators.ReadOnlyIterator;
import net.digitalid.utility.functional.iterators.ReadOnlyListIterator;
import net.digitalid.utility.rootclass.ValueCollector;
import net.digitalid.utility.validation.annotations.index.Index;
import net.digitalid.utility.validation.annotations.index.IndexForInsertion;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This class extends the {@link ArrayList} and makes it {@link FreezableInterface freezable}.
 * It is recommended to use only {@link ReadOnly} or {@link Immutable} types for the elements.
 */
@Freezable(ReadOnlyList.class)
public class FreezableArrayList<E> extends ArrayList<E> implements FreezableList<E> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected FreezableArrayList() {}
    
    /**
     * Returns a new freezable array list with no elements.
     */
    @Pure
    public static @Capturable <E> @Nonnull @NonFrozen FreezableArrayList<E> withNoElements() {
        return new FreezableArrayList<>();
    }
    
    /**
     * Returns a new freezable array list with the given element.
     */
    @Pure
    public static @Capturable <E> @Nonnull @NonFrozen FreezableArrayList<E> with(@Captured E element) {
        final @Nonnull FreezableArrayList<E> list = new FreezableArrayList<>();
        list.add(element);
        return list;
    }
    
    protected FreezableArrayList(@NonNegative int initialCapacity) {
        super(initialCapacity);
    }
    
    /**
     * Returns a new freezable array list with the given initial capacity.
     */
    @Pure
    public static @Capturable <E> @Nonnull @NonFrozen FreezableArrayList<E> withCapacity(@NonNegative int initialCapacity) {
        return new FreezableArrayList<>(initialCapacity);
    }
    
    /**
     * Returns a new freezable array list with the given elements or null if the given array is null.
     */
    @Pure
    @SafeVarargs
    public static @Capturable <E> @Nonnull @NonFrozen FreezableArrayList<E> with(@Captured  E... elements) {
        if (elements == null) { return null; }
        final @Nonnull FreezableArrayList<E> list = new FreezableArrayList<>(elements.length);
        list.addAll(Arrays.asList(elements));
        return list;
    }
    
    protected FreezableArrayList(@NonNegative int initialCapacity, @NonCaptured @Unmodified @Nonnull Iterable<? extends E> iterable) {
        super(initialCapacity);
        
        for (E element : iterable) {
            super.add(element);
        }
    }
    
    /**
     * Returns a new freezable array list with the elements of the given collection or null if the given collection is null.
     */
    @Pure
    public static @Capturable <E> @NonFrozen FreezableArrayList<E> with(@NonCaptured @Unmodified Collection<? extends E> collection) {
        return collection == null ? null : new FreezableArrayList<>(collection.size(), collection);
    }
    
    /**
     * Returns a new freezable array list with the elements of the given iterable or null if the given iterable is null.
     */
    @Pure
    public static @Capturable <E> @NonFrozen FreezableArrayList<E> with(FiniteIterable<? extends E> iterable) {
        return iterable == null ? null : new FreezableArrayList<>(iterable.size(), iterable);
    }
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    private boolean frozen = false;
    
    @Pure
    @Override
    public boolean isFrozen() {
        return frozen;
    }
    
    @Impure
    @Override
    public @Nonnull @Frozen ReadOnlyList<E> freeze() {
        this.frozen = true;
        return this;
    }
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableArrayList<E> clone() {
        return new FreezableArrayList<>(size(), this);
    }
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull ReadOnlyIterator<E> iterator() {
        return ReadOnlyIterableIterator.with(super.iterator());
    }
    
    /* -------------------------------------------------- List -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonCapturable E get(@Index int index) {
        return super.get(index);
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
    public @NonCapturable @Nonnull FreezableList<E> subList(@Index int fromIndex, @IndexForInsertion int toIndex) {
        return BackedFreezableList.with(this, super.subList(fromIndex, toIndex));
    }
    
    /* -------------------------------------------------- Add -------------------------------------------------- */
    
    @Impure
    @Override
    @NonFrozenRecipient
    public boolean add(@Captured E element) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.add(element);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public void add(@IndexForInsertion int index, @Captured E element) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        super.add(index, element);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public boolean addAll(@NonCaptured @Unmodified @Nonnull Collection<? extends E> collection) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.addAll(collection);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public boolean addAll(@IndexForInsertion int index, @NonCaptured @Unmodified @Nonnull Collection<? extends E> collection) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.addAll(index, collection);
    }
    
    /* -------------------------------------------------- Remove -------------------------------------------------- */
    
    @Impure
    @Override
    @NonFrozenRecipient
    public @Capturable E remove(@Index int index) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.remove(index);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public boolean remove(@NonCaptured @Unmodified @Nullable Object object) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.remove(object);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    protected void removeRange(@Index int fromIndex, @IndexForInsertion int toIndex) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        super.removeRange(fromIndex, toIndex);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public boolean removeAll(@NonCaptured @Unmodified @Nonnull Collection<?> collection) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.removeAll(collection);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public boolean retainAll(@NonCaptured @Unmodified @Nonnull Collection<?> collection) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.retainAll(collection);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public void clear() {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        super.clear();
    }
    
    /* -------------------------------------------------- Set -------------------------------------------------- */
    
    @Impure
    @Override
    @NonFrozenRecipient
    public @Capturable E set(@Index int index, @Captured E element) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.set(index, element);
    }
    
    /* -------------------------------------------------- Capacity -------------------------------------------------- */
    
    @Impure
    @Override
    @NonFrozenRecipient
    public void ensureCapacity(@NonNegative int minCapacity) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        super.ensureCapacity(minCapacity);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public void trimToSize() {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        super.trimToSize();
    }
    
    /* -------------------------------------------------- Collect Values -------------------------------------------------- */
    
    @Override
    @SuppressWarnings("unchecked")
    public void collectValues(@Nonnull ValueCollector valueCollector) {
        final E firstElement = getFirst();
        if (firstElement == null) {
            valueCollector.setNull();
        } else {
            valueCollector.setList(this, (Class<E>) firstElement.getClass());
        }
    }
    
}
