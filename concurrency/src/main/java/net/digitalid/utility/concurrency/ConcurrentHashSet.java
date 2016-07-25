package net.digitalid.utility.concurrency;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.circumfixes.Brackets;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.generation.Default;
import net.digitalid.utility.validation.annotations.generation.Recover;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * Implements a concurrent hash set based on {@link ConcurrentHashMap}.
 * 
 * @param <E> the type of the elements of this set.
 */
@Mutable
// TODO: @GenerateBuilder
@GenerateSubclass
public abstract class ConcurrentHashSet<E> extends AbstractSet<E> implements ConcurrentSet<E> {
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    /**
     * Stores the elements of this concurrent hash set.
     */
    private final @Nonnull ConcurrentHashMap<E, Boolean> map;
    
    /**
     * Stores the set representation of the map.
     */
    private final @Nonnull Set<E> set;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    @Recover
    protected ConcurrentHashSet(@NonNegative @Default("16") int initialCapacity, @Positive @Default("0.75f") float loadFactor, @Positive @Default("1") int concurrencyLevel) {
        this.map = ConcurrentHashMap.withInitialCapacityAndLoadFactorAndConcurrencyLevel(initialCapacity, loadFactor, concurrencyLevel);
        this.set = map.keySet();
    }
    
    /**
     * @see ConcurrentHashMap#withInitialCapacityAndLoadFactorAndConcurrencyLevel((@net.digitalid.utility.validation.annotations.math.NonNegative :: int), (@net.digitalid.utility.validation.annotations.math.Positive :: float), (@net.digitalid.utility.validation.annotations.math.Positive :: int))
     */
    @Pure
    @Deprecated // TODO: Remove this method once the builder can be generated.
    public static @Capturable <E> @Nonnull ConcurrentHashSet<E> withInitialCapacityAndLoadFactorAndConcurrencyLevel(@NonNegative int initialCapacity, @Positive float loadFactor, @Positive int concurrencyLevel) {
        return new ConcurrentHashSetSubclass<>(initialCapacity, loadFactor, concurrencyLevel);
    }
    
    /**
     * @see ConcurrentHashMap#withInitialCapacityAndLoadFactor(int, float)
     */
    @Pure
    @Deprecated // TODO: Remove this method once the builder can be generated.
    public static @Capturable <E> @Nonnull ConcurrentHashSet<E> withInitialCapacityAndLoadFactor(@NonNegative int initialCapacity, @Positive float loadFactor) {
        return withInitialCapacityAndLoadFactorAndConcurrencyLevel(initialCapacity, loadFactor, 1);
    }
    
    /**
     * @see ConcurrentHashMap#withInitialCapacity(int)
     */
    @Pure
    @Deprecated // TODO: Remove this method once the builder can be generated.
    public static @Capturable <E> @Nonnull ConcurrentHashSet<E> withInitialCapacity(@NonNegative int initialCapacity) {
        return withInitialCapacityAndLoadFactor(initialCapacity, 0.75f);
    }
    
    /**
     * @see ConcurrentHashMap#withDefaultCapacity()
     */
    @Pure
    @Deprecated // TODO: Remove this method once the builder can be generated.
    public static @Capturable <E> @Nonnull ConcurrentHashSet<E> withDefaultCapacity() {
        return withInitialCapacity(16);
    }
    
    protected ConcurrentHashSet(@NonCaptured @Unmodified @Nonnull Set<? extends E> set) {
        this.map = ConcurrentHashMap.withInitialCapacity(set.size());
        this.set = map.keySet();
        addAll(set);
    }
    
    /**
     * Returns a new concurrent hash set with the elements of the given set or null if the given set is null.
     */
    @Pure
    public static @Capturable <E> ConcurrentHashSet<E> withElementsOf(@NonCaptured @Unmodified Set<? extends E> set) {
        return set == null ? null : new ConcurrentHashSetSubclass<>(set);
    }
    
    /* -------------------------------------------------- Set -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonNegative int size() {
        return map.size();
    }
    
    @Pure
    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }
    
    @Pure
    @Override
    public boolean contains(@NonCaptured @Unmodified @Nonnull Object object) {
        return map.containsKey(object);
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull Iterator<E> iterator() {
        return set.iterator();
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull Object[] toArray() {
        return set.toArray();
    }
    
    @Pure
    @Override
    @SuppressWarnings("SuspiciousToArrayCall")
    public @Capturable <T> @Nonnull T[] toArray(@NonCaptured @Modified @Nonnull T[] array) {
        return set.toArray(array);
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    @Impure
    @Override
    public boolean add(@Captured E element) {
        return map.put(element, true) == null;
    }
    
    @Impure
    @Override
    public boolean remove(@NonCaptured @Unmodified @Nullable Object object) {
        return map.remove(object) != null;
    }
    
    @Pure
    @Override
    public boolean containsAll(@NonCaptured @Unmodified @Nonnull Collection<?> collection) {
        return set.containsAll(collection);
    }
    
    @Impure
    @Override
    public boolean addAll(@NonCaptured @Unmodified @Nonnull Collection<? extends E> collection) {
        boolean changed = false;
        for (E element : collection) {
            if (add(element)) { changed = true; }
        }
        return changed;
    }
    
    @Impure
    @Override
    public boolean retainAll(@NonCaptured @Unmodified @Nonnull Collection<?> collection) {
        boolean changed = false;
        for (E element : this) {
            if (!collection.contains(element)) {
                remove(element);
                changed = true;
            }
        }
        return changed;
    }
    
    @Impure
    @Override
    public void clear() {
        map.clear();
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return FiniteIterable.of(this).map((element) -> (element.toString())).join(Brackets.CURLY);
    }
    
}
