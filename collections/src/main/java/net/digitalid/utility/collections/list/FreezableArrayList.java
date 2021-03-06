/*
 * Copyright (C) 2017 Synacts GmbH, Switzerland (info@synacts.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import net.digitalid.utility.collections.collection.FreezableCollection;
import net.digitalid.utility.collections.iterator.FreezableIterator;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.functional.iterators.ReadOnlyIterableIterator;
import net.digitalid.utility.functional.iterators.ReadOnlyIterator;
import net.digitalid.utility.functional.iterators.ReadOnlyListIterator;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.index.Index;
import net.digitalid.utility.validation.annotations.index.IndexForInsertion;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This class extends the {@link ArrayList} and makes it {@link FreezableInterface freezable}.
 * It is recommended to use only {@link ReadOnly} or {@link Immutable} types for the elements.
 */
@GenerateSubclass
@Freezable(ReadOnlyList.class)
public abstract class FreezableArrayList<E> extends ArrayList<E> implements FreezableList<E> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected FreezableArrayList() {}
    
    /**
     * Returns a new freezable array list with no elements.
     */
    @Pure
    public static @Capturable <E> @Nonnull @NonFrozen FreezableArrayList<E> withNoElements() {
        return new FreezableArrayListSubclass<>();
    }
    
    /**
     * Returns a new freezable array list with the given element.
     */
    @Pure
    public static @Capturable <E> @Nonnull @NonFrozen FreezableArrayList<E> withElement(@Captured E element) {
        final @Nonnull FreezableArrayList<E> list = new FreezableArrayListSubclass<>();
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
    public static @Capturable <E> @Nonnull @NonFrozen FreezableArrayList<E> withInitialCapacity(@NonNegative int initialCapacity) {
        return new FreezableArrayListSubclass<>(initialCapacity);
    }
    
    protected FreezableArrayList(@NonNegative int initialCapacity, @NonCaptured @Unmodified @Nonnull Iterable<? extends E> iterable) {
        super(initialCapacity);
        
        for (E element : iterable) {
            super.add(element);
        }
    }
    
    /**
     * Returns a new freezable array list with the given elements or null if the given array is null.
     */
    @Pure
    @SafeVarargs
    public static @Capturable <E> @NonFrozen FreezableArrayList<E> withElements(@NonCaptured @Unmodified E... elements) {
        return elements == null ? null : new FreezableArrayListSubclass<>(elements.length, Arrays.asList(elements));
    }
    
    /**
     * Returns a new freezable array list with the elements of the given iterable or null if the given iterable is null.
     */
    @Pure
    public static @Capturable <E> @NonFrozen FreezableArrayList<E> withElementsOf(FiniteIterable<? extends E> iterable) {
        return iterable == null ? null : new FreezableArrayListSubclass<>(iterable.size(), iterable);
    }
    
    /**
     * Returns a new freezable array list with the elements of the given collection or null if the given collection is null.
     */
    @Pure
    public static @Capturable <E> @NonFrozen FreezableArrayList<E> withElementsOf(@NonCaptured @Unmodified Collection<? extends E> collection) {
        return collection == null ? null : new FreezableArrayListSubclass<>(collection.size(), collection);
    }
    
    /**
     * Returns a new freezable array list with the elements of the given freezable collection or null if the given collection is null.
     */
    @Pure
    public static @Capturable <E> @NonFrozen FreezableArrayList<E> withElementsOf(@NonCaptured @Unmodified FreezableCollection<? extends E> collection) {
        return collection == null ? null : new FreezableArrayListSubclass<>(collection.size(), collection);
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
    @NonFrozenRecipient
    public @Nonnull @Frozen ReadOnlyList<E> freeze() {
        this.frozen = true;
        return this;
    }
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableArrayList<E> clone() {
        return new FreezableArrayListSubclass<>(size(), this);
    }
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull ReadOnlyIterator<E> iterator() {
        return ReadOnlyIterableIterator.with(super.iterator());
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull FreezableIterator<E> freezableIterator() {
        return FreezableIterator.with(super.iterator(), this);
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
        return super.add(element);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public void add(@IndexForInsertion int index, @Captured E element) {
        super.add(index, element);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public boolean addAll(@NonCaptured @Unmodified @Nonnull Collection<? extends E> collection) {
        return super.addAll(collection);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public boolean addAll(@IndexForInsertion int index, @NonCaptured @Unmodified @Nonnull Collection<? extends E> collection) {
        return super.addAll(index, collection);
    }
    
    /* -------------------------------------------------- Remove -------------------------------------------------- */
    
    @Impure
    @Override
    @NonFrozenRecipient
    public @Capturable E remove(@Index int index) {
        return super.remove(index);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public boolean remove(@NonCaptured @Unmodified @Nullable Object object) {
        return super.remove(object);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    protected void removeRange(@Index int fromIndex, @IndexForInsertion int toIndex) {
        super.removeRange(fromIndex, toIndex);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public boolean removeAll(@NonCaptured @Unmodified @Nonnull Collection<?> collection) {
        return super.removeAll(collection);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public boolean retainAll(@NonCaptured @Unmodified @Nonnull Collection<?> collection) {
        return super.retainAll(collection);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public void clear() {
        super.clear();
    }
    
    /* -------------------------------------------------- Set -------------------------------------------------- */
    
    @Impure
    @Override
    @NonFrozenRecipient
    public @Capturable E set(@Index int index, @Captured E element) {
        return super.set(index, element);
    }
    
    /* -------------------------------------------------- Capacity -------------------------------------------------- */
    
    @Impure
    @Override
    @NonFrozenRecipient
    public void ensureCapacity(@NonNegative int minCapacity) {
        super.ensureCapacity(minCapacity);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public void trimToSize() {
        super.trimToSize();
    }
    
}
