package net.digitalid.utility.collections.set;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.functional.fixes.Brackets;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.functional.iterators.ReadOnlyIterableIterator;
import net.digitalid.utility.functional.iterators.ReadOnlyIterator;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This class extends the {@link HashSet} and makes it {@link FreezableInterface freezable}.
 * It is recommended to use only {@link ReadOnly} or {@link Immutable} types for the elements.
 */
@GenerateSubclass
@Freezable(ReadOnlySet.class)
public abstract class FreezableHashSet<E> extends HashSet<E> implements FreezableSet<E> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected FreezableHashSet(@NonNegative int initialCapacity, @Positive float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    /**
     * Returns a new freezable hash set with the given initial capacity and the given load factor.
     */
    @Pure
    public static @Capturable <E> @Nonnull @NonFrozen FreezableHashSet<E> withCapacityAndFactor(@NonNegative int initialCapacity, @Positive float loadFactor) {
        return new FreezableHashSetSubclass<>(initialCapacity, loadFactor);
    }
    
    /**
     * Returns a new freezable hash set with the given initial capacity.
     */
    @Pure
    public static @Capturable <E> @Nonnull @NonFrozen FreezableHashSet<E> withCapacity(@NonNegative int initialCapacity) {
        return withCapacityAndFactor(initialCapacity, 0.75f);
    }
    
    /**
     * Returns a new freezable hash set.
     */
    @Pure
    public static @Capturable <E> @Nonnull @NonFrozen FreezableHashSet<E> withDefaultCapacity() {
        return withCapacity(16);
    }
    
    /**
     * Returns a new freezable hash set with the given element.
     */
    @Pure
    public static @Capturable <E> @Nonnull @NonFrozen FreezableHashSet<E> withElement(@Captured E element) {
        final @Nonnull FreezableHashSet<E> set = withDefaultCapacity();
        set.add(element);
        return set;
    }
    
    /**
     * Returns a new freezable hash set with the given elements or null if the given array is null.
     */
    @Pure
    @SafeVarargs
    public static @Capturable <E> @Nonnull @NonFrozen FreezableHashSet<E> withElements(@Captured E... elements) {
        if (elements == null) { return null; }
        final @Nonnull FreezableHashSet<E> set = withCapacity(elements.length);
        set.addAll(Arrays.asList(elements));
        return set;
    }
    
    protected FreezableHashSet(@NonNegative int initialCapacity, @NonCaptured @Unmodified @Nonnull Iterable<? extends E> iterable) {
        super(initialCapacity);
        
        for (E element : iterable) {
            super.add(element);
        }
    }
    
    /**
     * Returns a new freezable hash set with the elements of the given collection or null if the given collection is null.
     */
    @Pure
    public static @Capturable <E> @NonFrozen FreezableHashSet<E> withElementsOf(@NonCaptured @Unmodified Collection<? extends E> collection) {
        return collection == null ? null : new FreezableHashSetSubclass<>(collection.size(), collection);
    }
    
    /**
     * Returns a new freezable hash set with the elements of the given iterable or null if the given iterable is null.
     */
    @Pure
    public static @Capturable <E> @NonFrozen FreezableHashSet<E> withElementsOf(FiniteIterable<? extends E> iterable) {
        return iterable == null ? null : new FreezableHashSetSubclass<>(iterable.size(), iterable);
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
    public @Capturable @Nonnull @NonFrozen FreezableHashSet<E> clone() {
        return new FreezableHashSetSubclass<>(size(), this);
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
        return super.add(element);
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
    public boolean remove(@NonCaptured @Unmodified @Nullable Object object) {
        return super.remove(object);
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
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return join(Brackets.CURLY);
    }
    
}
