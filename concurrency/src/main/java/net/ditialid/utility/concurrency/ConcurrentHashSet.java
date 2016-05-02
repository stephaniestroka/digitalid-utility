package net.ditialid.utility.concurrency;

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
import net.digitalid.utility.functional.fixes.Brackets;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * Implements a concurrent hash set based on {@link ConcurrentHashMap}.
 * 
 * @param <E> the type of the elements of this set.
 */
@Mutable
public class ConcurrentHashSet<E> extends AbstractSet<E> implements ConcurrentSet<E> {
    
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
    
    /**
     * @see ConcurrentHashMap#ConcurrentHashMap(int, float, int)
     */
    protected ConcurrentHashSet(@NonNegative int initialCapacity, @Positive float loadFactor, @Positive int concurrencyLevel) {
        this.map = ConcurrentHashMap.get(initialCapacity, loadFactor, concurrencyLevel);
        this.set = map.keySet();
    }
    
    /**
     * @see ConcurrentHashMap#ConcurrentHashMap(int, float, int)
     */
    @Pure
    public static <E> @Capturable @Nonnull ConcurrentHashSet<E> get(@NonNegative int initialCapacity, @Positive float loadFactor, @Positive int concurrencyLevel) {
        return new ConcurrentHashSet<>(initialCapacity, loadFactor, concurrencyLevel);
    }
    
    /**
     * @see ConcurrentHashMap#get(int, float)
     */
    @Pure
    public static <E> @Capturable @Nonnull ConcurrentHashSet<E> get(@NonNegative int initialCapacity, @Positive float loadFactor) {
        return get(initialCapacity, loadFactor, 16);
    }
    
    /**
     * @see ConcurrentHashMap#get(int)
     */
    @Pure
    public static <E> @Capturable @Nonnull ConcurrentHashSet<E> get(@NonNegative int initialCapacity) {
        return get(initialCapacity, 0.75f);
    }
    
    /**
     * @see ConcurrentHashMap#get()
     */
    @Pure
    public static <E> @Capturable @Nonnull ConcurrentHashSet<E> get() {
        return get(16);
    }
    
    /**
     * @see ConcurrentHashMap#ConcurrentHashMap(java.util.Map)
     */
    protected ConcurrentHashSet(@NonCaptured @Unmodified Set<? extends E> set) {
        this.map = ConcurrentHashMap.get(set.size());
        this.set = map.keySet();
        addAll(set);
    }
    
    /**
     * @see ConcurrentHashMap#ConcurrentHashMap(java.util.Map)
     */
    @Pure
    public static <E> @Capturable @Nonnull ConcurrentHashSet<E> getNonNullable(@NonCaptured @Unmodified @Nonnull Set<? extends E> set) {
        return new ConcurrentHashSet<>(set);
    }
    
    /**
     * @see ConcurrentHashMap#ConcurrentHashMap(java.util.Map)
     */
    @Pure
    public static <E> @Capturable @Nullable ConcurrentHashSet<E> getNullable(@NonCaptured @Unmodified @Nullable Set<? extends E> set) {
        return set == null ? null : getNonNullable(set);
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
    public <T> @Capturable @Nonnull T[] toArray(@NonCaptured @Modified @Nonnull T[] array) {
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
    public boolean remove(@NonCaptured @Unmodified Object object) {
        return map.remove(object) != null;
    }
    
    @Pure
    @Override
    public boolean containsAll(@NonCaptured @Unmodified Collection<?> collection) {
        return set.containsAll(collection);
    }
    
    @Impure
    @Override
    public boolean addAll(@NonCaptured @Unmodified Collection<? extends E> collection) {
        boolean changed = false;
        for (E element : collection) {
            if (add(element)) { changed = true; }
        }
        return changed;
    }
    
    @Impure
    @Override
    public boolean retainAll(@NonCaptured @Unmodified Collection<?> collection) {
        boolean changed = false;
        for (final E element : this) {
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
    public String toString() {
        return FiniteIterable.of(this).map((element) -> (element.toString())).join(Brackets.CURLY);
    }
    
}
