package net.ditialid.utility.concurrency;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * Extends Java's {@link java.util.concurrent.ConcurrentMap ConcurrentMap} interface with useful methods.
 * 
 * @param <K> the type of the keys of this map.
 * @param <V> the type of the values of this map.
 * 
 * @see ConcurrentHashMap
 */
@Mutable
public interface ConcurrentMap<K, V> extends java.util.concurrent.ConcurrentMap<K, V>, Cloneable {
    
    /**
     * Associates the given value with the given key, if the
     * given key is not already associated with another value.
     * 
     * @return the value that is now associated with the given key.
     */
    @Impure
    public @NonCapturable @Nonnull V putIfAbsentElseReturnPresent(@Captured @Nonnull K key, @Captured @Nonnull V value);
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    /**
     * Returns a shallow copy of this map (the keys and values themselves are not cloned).
     */
    @Pure
    public @Capturable @Nonnull ConcurrentMap<K, V> clone();
    
}
