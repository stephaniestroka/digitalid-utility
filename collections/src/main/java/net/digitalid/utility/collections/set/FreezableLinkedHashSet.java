package net.digitalid.utility.collections.set;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.functional.fixes.Brackets;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.functional.iterators.ReadOnlyIterableIterator;
import net.digitalid.utility.functional.iterators.ReadOnlyIterator;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This class extends the {@link LinkedHashSet} and makes it {@link FreezableInterface freezable}.
 * It is recommended to use only {@link ReadOnly} or {@link Immutable} types for the elements.
 */
@Freezable(ReadOnlySet.class)
public class FreezableLinkedHashSet<E> extends LinkedHashSet<E> implements FreezableSet<E> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected FreezableLinkedHashSet(@NonNegative int initialCapacity, @Positive float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    /**
     * Returns a new freezable linked hash set with the given initial capacity and the given load factor.
     */
    @Pure
    public static @Capturable <E> @Nonnull @NonFrozen FreezableLinkedHashSet<E> with(@NonNegative int initialCapacity, @Positive float loadFactor) {
        return new FreezableLinkedHashSet<>(initialCapacity, loadFactor);
    }
    
    /**
     * Returns a new freezable linked hash set with the given initial capacity.
     */
    @Pure
    public static @Capturable <E> @Nonnull @NonFrozen FreezableLinkedHashSet<E> with(@NonNegative int initialCapacity) {
        return with(initialCapacity, 0.75f);
    }
    
    /**
     * Returns a new freezable linked hash set.
     */
    @Pure
    public static @Capturable <E> @Nonnull @NonFrozen FreezableLinkedHashSet<E> with() {
        return with(16);
    }
    
    /**
     * Returns a new freezable linked hash set with the given element.
     */
    @Pure
    public static @Capturable <E> @Nonnull @NonFrozen FreezableLinkedHashSet<E> with(@Captured E element) {
        final @Nonnull FreezableLinkedHashSet<E> set = with();
        set.add(element);
        return set;
    }
    
    /**
     * Returns a new freezable linked hash set with the given elements or null if the given array is null.
     */
    @Pure
    @SafeVarargs
    public static @Capturable <E> @Nonnull @NonFrozen FreezableLinkedHashSet<E> with(@Captured E... elements) {
        if (elements == null) { return null; }
        final @Nonnull FreezableLinkedHashSet<E> set = with(elements.length);
        set.addAll(Arrays.asList(elements));
        return set;
    }
    
    protected FreezableLinkedHashSet(@NonNegative int initialCapacity, @NonCaptured @Unmodified @Nonnull Iterable<? extends E> iterable) {
        super(initialCapacity);
        
        for (E element : iterable) {
            super.add(element);
        }
    }
    
    /**
     * Returns a new freezable linked hash set with the elements of the given collection or null if the given collection is null.
     */
    @Pure
    public static @Capturable <E> @NonFrozen FreezableLinkedHashSet<E> with(@NonCaptured @Unmodified Collection<? extends E> collection) {
        return collection == null ? null : new FreezableLinkedHashSet<>(collection.size(), collection);
    }
    
    /**
     * Returns a new freezable linked hash set with the elements of the given iterable or null if the given iterable is null.
     */
    @Pure
    public static @Capturable <E> @NonFrozen FreezableLinkedHashSet<E> with(FiniteIterable<? extends E> iterable) {
        return iterable == null ? null : new FreezableLinkedHashSet<>(iterable.size(), iterable);
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
    public @Nonnull @Frozen ReadOnlySet<E> freeze() {
        this.frozen = true;
        return this;
    }
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableLinkedHashSet<E> clone() {
        return new FreezableLinkedHashSet<>(size(), this);
    }
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull ReadOnlyIterator<E> iterator() {
        return ReadOnlyIterableIterator.with(super.iterator());
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
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
    public boolean addAll(@NonCaptured @Unmodified @Nonnull Collection<? extends E> collection) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.addAll(collection);
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
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return join(Brackets.CURLY);
    }
    
}
