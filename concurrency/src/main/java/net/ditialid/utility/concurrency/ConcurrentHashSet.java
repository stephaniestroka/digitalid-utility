package net.ditialid.utility.concurrency;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;


import net.digitalid.utility.functional.fixes.Brackets;
import net.digitalid.utility.functional.iterables.FiniteIterable;

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
    private final ConcurrentHashMap<E, Boolean> map;
    
    /**
     * Stores the set representation of the map.
     */
    private final Set<E> set;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * @see ConcurrentHashMap#ConcurrentHashMap(int, float, int)
     */
    protected ConcurrentHashSet(int initialCapacity, float loadFactor, int concurrencyLevel) {
        this.map = ConcurrentHashMap.get(initialCapacity, loadFactor, concurrencyLevel);
        this.set = map.keySet();
    }
    
    /**
     * @see ConcurrentHashMap#ConcurrentHashMap(int, float, int)
     */
    public static <E> ConcurrentHashSet<E> get(int initialCapacity, float loadFactor, int concurrencyLevel) {
        return new ConcurrentHashSet<>(initialCapacity, loadFactor, concurrencyLevel);
    }
    
    /**
     * @see ConcurrentHashMap#get(int, float)
     */
    public static <E> ConcurrentHashSet<E> get(int initialCapacity, float loadFactor) {
        return get(initialCapacity, loadFactor, 16);
    }
    
    /**
     * @see ConcurrentHashMap#get(int)
     */
    public static <E> ConcurrentHashSet<E> get(int initialCapacity) {
        return get(initialCapacity, 0.75f);
    }
    
    /**
     * @see ConcurrentHashMap#get()
     */
    public static <E> ConcurrentHashSet<E> get() {
        return get(16);
    }
    
    /**
     * @see ConcurrentHashMap#ConcurrentHashMap(java.util.Map)
     */
    protected ConcurrentHashSet(Set<? extends E> set) {
        this.map = ConcurrentHashMap.get(set.size());
        this.set = map.keySet();
        addAll(set);
    }
    
    /**
     * @see ConcurrentHashMap#ConcurrentHashMap(java.util.Map)
     */
    public static <E> ConcurrentHashSet<E> getNonNullable(Set<? extends E> set) {
        return new ConcurrentHashSet<>(set);
    }
    
    /**
     * @see ConcurrentHashMap#ConcurrentHashMap(java.util.Map)
     */
    public static <E> ConcurrentHashSet<E> getNullable(Set<? extends E> set) {
        return set == null ? null : getNonNullable(set);
    }
    
    /* -------------------------------------------------- Set -------------------------------------------------- */
    
    @Override
    public int size() {
        return map.size();
    }
    
    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }
    
    @Override
    public boolean contains(Object object) {
        return map.containsKey(object);
    }
    
    @Override
    public Iterator<E> iterator() {
        return set.iterator();
    }
    
    @Override
    public Object[] toArray() {
        return set.toArray();
    }
    
    @Override
    @SuppressWarnings("SuspiciousToArrayCall")
    public <T> T[] toArray(T[] array) {
        return set.toArray(array);
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    @Override
    public boolean add(E element) {
        return map.put(element, true) == null;
    }
    
    @Override
    public boolean remove(Object object) {
        return map.remove(object) != null;
    }
    
    @Override
    public boolean containsAll(Collection<?> collection) {
        return set.containsAll(collection);
    }
    
    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean changed = false;
        for (final E element : collection) {
            if (add(element)) { changed = true; }
        }
        return changed;
    }
    
    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean changed = false;
        for (final E element : this) {
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
    
    @Override
    public String toString() {
        return FiniteIterable.of(this).map((element) -> (element.toString())).join(Brackets.CURLY);
    }
    
}
