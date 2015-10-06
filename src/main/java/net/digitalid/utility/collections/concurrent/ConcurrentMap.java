package net.digitalid.utility.collections.concurrent;

import javax.annotation.Nonnull;
import net.digitalid.utility.annotations.reference.Capturable;
import net.digitalid.utility.annotations.state.Pure;

/**
 * Extends Java's {@link java.util.concurrent.ConcurrentMap ConcurrentMap} interface with more useful methods.
 * 
 * @param <K> the type of the keys of this map.
 * @param <V> the type of the values of this map.
 * 
 * @see ConcurrentHashMap
 * 
 * @author Kaspar Etter (kaspar.etter@digitalid.net)
 * @version 1.0.0
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
    public @Nonnull V putIfAbsentElseReturnPresent(@Nonnull K key, @Nonnull V value);
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Cloneable –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    /**
     * Returns a shallow copy of this map (the keys and values themselves are not cloned).
     *
     * @return a shallow copy of this map
     */
    @Pure
    public @Capturable @Nonnull ConcurrentMap<K, V> clone();
    
}
