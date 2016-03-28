package net.ditialid.utility.concurrency;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.function.unary.NullableToNonNullUnaryFunction;
import net.digitalid.utility.functional.iterable.NullableIterable;
import net.digitalid.utility.functional.string.Brackets;
import net.digitalid.utility.functional.string.IterableConverter;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.reference.Capturable;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * Implements a concurrent hash set based on {@link ConcurrentHashMap}.
 * 
 * @param <E> the type of the elements of this set.
 */
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
    public static @Capturable @Nonnull <E> ConcurrentHashSet<E> get(@NonNegative int initialCapacity, @Positive float loadFactor, @Positive int concurrencyLevel) {
        return new ConcurrentHashSet<>(initialCapacity, loadFactor, concurrencyLevel);
    }
    
    /**
     * @see ConcurrentHashMap#get(int, float)
     */
    @Pure
    public static @Capturable @Nonnull <E> ConcurrentHashSet<E> get(@NonNegative int initialCapacity, @Positive float loadFactor) {
        return get(initialCapacity, loadFactor, 16);
    }
    
    /**
     * @see ConcurrentHashMap#get(int)
     */
    @Pure
    public static @Capturable @Nonnull <E> ConcurrentHashSet<E> get(@NonNegative int initialCapacity) {
        return get(initialCapacity, 0.75f);
    }
    
    /**
     * @see ConcurrentHashMap#get()
     */
    @Pure
    public static @Capturable @Nonnull <E> ConcurrentHashSet<E> get() {
        return get(16);
    }
    
    /**
     * @see ConcurrentHashMap#ConcurrentHashMap(java.util.Map)
     */
    protected ConcurrentHashSet(@Nonnull Set<? extends E> set) {
        this.map = ConcurrentHashMap.get(set.size());
        this.set = map.keySet();
        addAll(set);
    }
    
    /**
     * @see ConcurrentHashMap#ConcurrentHashMap(java.util.Map)
     */
    @Pure
    public static @Capturable @Nonnull <E> ConcurrentHashSet<E> getNonNullable(@Nonnull Set<? extends E> set) {
        return new ConcurrentHashSet<>(set);
    }
    
    /**
     * @see ConcurrentHashMap#ConcurrentHashMap(java.util.Map)
     */
    @Pure
    public static @Capturable @Nullable <E> ConcurrentHashSet<E> getNullable(@Nullable Set<? extends E> set) {
        return set == null ? null : getNonNullable(set);
    }
    
    /* -------------------------------------------------- Set -------------------------------------------------- */
    
    @Pure
    @Override
    public int size() {
        return map.size();
    }
    
    @Pure
    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }
    
    @Pure
    @Override
    public boolean contains(@Nonnull Object object) {
        return map.containsKey(object);
    }
    
    @Pure
    @Override
    public Iterator<E> iterator() {
        return set.iterator();
    }
    
    @Pure
    @Override
    public Object[] toArray() {
        return set.toArray();
    }
    
    @Pure
    @Override
    @SuppressWarnings("SuspiciousToArrayCall")
    public <T> T[] toArray(@Nonnull T[] array) {
        return set.toArray(array);
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    @Override
    public boolean add(@Nonnull E element) {
        return map.put(element, true) == null;
    }
    
    @Override
    public boolean remove(@Nonnull Object object) {
        return map.remove(object) != null;
    }
    
    @Pure
    @Override
    public boolean containsAll(@Nonnull Collection<?> collection) {
        return set.containsAll(collection);
    }
    
    @Override
    public boolean addAll(@Nonnull Collection<? extends E> collection) {
        boolean changed = false;
        for (final @Nonnull E element : collection) {
            if (add(element)) { changed = true; }
        }
        return changed;
    }
    
    @Override
    public boolean retainAll(@Nonnull Collection<?> collection) {
        boolean changed = false;
        for (final @Nonnull E element : this) {
            if (!collection.contains(element)) {
                remove(element);
                changed = true;
            }
        }
        return changed;
    }
    
    @Override
    public void clear() {
        map.clear();
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    private final NullableToNonNullUnaryFunction<E, String> toStringFunction = new NullableToNonNullUnaryFunction<E, String>() {
        
        @Override
        public @Nonnull String apply(@Nullable E element) {
            if (element == null) {
                return "null";
            } else {
                return element.toString();
            }
        }
        
    };
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return IterableConverter.toString(NullableIterable.ofNullableElements(this).map(toStringFunction), Brackets.CURLY);
    }
    
}
