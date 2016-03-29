package net.ditialid.utility.concurrency;

import java.util.Map;

import net.digitalid.utility.functional.fixes.Brackets;
import net.digitalid.utility.functional.iterables.FiniteIterable;

/**
 * Extends Java's {@link java.util.concurrent.ConcurrentHashMap ConcurrentHashMap} implementation with more useful methods.
 * 
 * @param <K> the type of the keys of this map.
 * @param <V> the type of the values of this map.
 */
public class ConcurrentHashMap<K, V> extends java.util.concurrent.ConcurrentHashMap<K, V> implements ConcurrentMap<K, V> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * @see java.util.concurrent.ConcurrentHashMap#ConcurrentHashMap(int, float, int)
     */
    protected ConcurrentHashMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
        super(initialCapacity, loadFactor, concurrencyLevel);
    }
    
    /**
     * @see java.util.concurrent.ConcurrentHashMap#ConcurrentHashMap(int, float, int)
     */
    public static <K, V> ConcurrentHashMap<K, V> get(int initialCapacity, float loadFactor, int concurrencyLevel) {
        return new ConcurrentHashMap<>(initialCapacity, loadFactor, concurrencyLevel);
    }
    
    /**
     * @see java.util.concurrent.ConcurrentHashMap#ConcurrentHashMap(int, float)
     */
    public static <K, V> ConcurrentHashMap<K, V> get(int initialCapacity, float loadFactor) {
        return get(initialCapacity, loadFactor, 16);
    }
    
    /**
     * @see java.util.concurrent.ConcurrentHashMap#ConcurrentHashMap(int)
     */
    public static <K, V> ConcurrentHashMap<K, V> get(int initialCapacity) {
        return get(initialCapacity, 0.75f);
    }
    
    /**
     * @see java.util.concurrent.ConcurrentHashMap#ConcurrentHashMap()
     */
    public static <K, V> ConcurrentHashMap<K, V> get() {
        return get(16);
    }
    
    /**
     * @see java.util.concurrent.ConcurrentHashMap#ConcurrentHashMap(java.util.Map)
     */
    protected ConcurrentHashMap(Map<? extends K, ? extends V> map) {
        super(map);
    }
    
    /**
     * @see java.util.concurrent.ConcurrentHashMap#ConcurrentHashMap(java.util.Map)
     */
    public static <K, V> ConcurrentHashMap<K, V> getNonNullable(Map<? extends K, ? extends V> map) {
        return new ConcurrentHashMap<>(map);
    }
    
    /**
     * @see java.util.concurrent.ConcurrentHashMap#ConcurrentHashMap(java.util.Map)
     */
    public static <K, V> ConcurrentHashMap<K, V> getNullable(Map<? extends K, ? extends V> map) {
        return map == null ? null : getNonNullable(map);
    }
    
    /* -------------------------------------------------- ConcurrentMap -------------------------------------------------- */
    
    @Override
    public V putIfAbsentElseReturnPresent(K key, V value) {
        final V previous = putIfAbsent(key, value);
        if (previous == null) { return value; }
        else { return previous; }
    }
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Override
    @SuppressWarnings("unchecked")
    public ConcurrentHashMap<K, V> clone() {
        try {
            return (ConcurrentHashMap<K, V>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("This should never happen, because ConcurrentHashMap implements Cloneable", e);
        }
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Override
    public String toString() {
        return FiniteIterable.of(entrySet()).map((entry) -> (entry == null ? "null" : entry.getKey() + ": " + entry.getValue())).join(Brackets.CURLY);
    }
    
}
