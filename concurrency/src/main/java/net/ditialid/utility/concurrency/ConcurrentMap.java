package net.ditialid.utility.concurrency;

/**
 * Extends Java's {@link java.util.concurrent.ConcurrentMap ConcurrentMap} interface with more useful methods.
 * 
 * @param <K> the type of the keys of this map.
 * @param <V> the type of the values of this map.
 * 
 * @see ConcurrentHashMap
 */
public interface ConcurrentMap<K, V> extends java.util.concurrent.ConcurrentMap<K, V>, Cloneable {
    
    /**
     * Associates the given value with the given key, if the
     * given key is not already associated with a value.
     * 
     * @param key the key to be associated with the given value.
     * @param value the value to be associated with the given key.
     * 
     * @return the value that is now associated with the given key.
     */
    public V putIfAbsentElseReturnPresent(K key, V value);
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    /**
     * Returns a shallow copy of this map (the keys and values themselves are not cloned).
     *
     * @return a shallow copy of this map
     */
    public ConcurrentMap<K, V> clone();
    
}
