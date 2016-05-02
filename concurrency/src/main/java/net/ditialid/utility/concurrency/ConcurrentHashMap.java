package net.ditialid.utility.concurrency;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.functional.fixes.Brackets;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * Extends Java's {@link java.util.concurrent.ConcurrentHashMap ConcurrentHashMap} implementation with more useful methods.
 * 
 * @param <K> the type of the keys of this map.
 * @param <V> the type of the values of this map.
 */
@Mutable
public class ConcurrentHashMap<K, V> extends java.util.concurrent.ConcurrentHashMap<K, V> implements ConcurrentMap<K, V> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * @see java.util.concurrent.ConcurrentHashMap#ConcurrentHashMap(int, float, int)
     */
    protected ConcurrentHashMap(@NonNegative int initialCapacity, @Positive float loadFactor, @Positive int concurrencyLevel) {
        super(initialCapacity, loadFactor, concurrencyLevel);
    }
    
    /**
     * @see java.util.concurrent.ConcurrentHashMap#ConcurrentHashMap(int, float, int)
     */
    @Pure
    public static <K, V> @Capturable @Nonnull ConcurrentHashMap<K, V> get(@NonNegative int initialCapacity, @Positive float loadFactor, @Positive int concurrencyLevel) {
        return new ConcurrentHashMap<>(initialCapacity, loadFactor, concurrencyLevel);
    }
    
    /**
     * @see java.util.concurrent.ConcurrentHashMap#ConcurrentHashMap(int, float)
     */
    @Pure
    public static <K, V> @Capturable @Nonnull ConcurrentHashMap<K, V> get(@NonNegative int initialCapacity, @Positive float loadFactor) {
        return get(initialCapacity, loadFactor, 16);
    }
    
    /**
     * @see java.util.concurrent.ConcurrentHashMap#ConcurrentHashMap(int)
     */
    @Pure
    public static <K, V> @Capturable @Nonnull ConcurrentHashMap<K, V> get(@NonNegative int initialCapacity) {
        return get(initialCapacity, 0.75f);
    }
    
    /**
     * @see java.util.concurrent.ConcurrentHashMap#ConcurrentHashMap()
     */
    @Pure
    public static <K, V> @Capturable @Nonnull ConcurrentHashMap<K, V> get() {
        return get(16);
    }
    
    /**
     * @see java.util.concurrent.ConcurrentHashMap#ConcurrentHashMap(java.util.Map)
     */
    protected ConcurrentHashMap(@NonCaptured @Unmodified @Nonnull Map<? extends K, ? extends V> map) {
        super(map);
    }
    
    /**
     * @see java.util.concurrent.ConcurrentHashMap#ConcurrentHashMap(java.util.Map)
     */
    @Pure
    public static <K, V> @Capturable @Nonnull ConcurrentHashMap<K, V> getNonNullable(@NonCaptured @Unmodified @Nonnull Map<? extends K, ? extends V> map) {
        return new ConcurrentHashMap<>(map);
    }
    
    /**
     * @see java.util.concurrent.ConcurrentHashMap#ConcurrentHashMap(java.util.Map)
     */
    @Pure
    public static <K, V> @Capturable @Nullable ConcurrentHashMap<K, V> getNullable(@NonCaptured @Unmodified @Nullable Map<? extends K, ? extends V> map) {
        return map == null ? null : getNonNullable(map);
    }
    
    /* -------------------------------------------------- ConcurrentMap -------------------------------------------------- */
    
    @Impure
    @Override
    public @NonCapturable @Nonnull V putIfAbsentElseReturnPresent(@Captured @Nonnull K key, @Captured @Nonnull V value) {
        final @Nullable V previous = putIfAbsent(key, value);
        if (previous == null) { return value; }
        else { return previous; }
    }
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    @SuppressWarnings("unchecked")
    public @Capturable @Nonnull ConcurrentHashMap<K, V> clone() {
        try {
            return (ConcurrentHashMap<K, V>) super.clone();
        } catch (@Nonnull CloneNotSupportedException exception) {
            throw new RuntimeException("This should never happen because ConcurrentHashMap implements Cloneable.", exception);
        }
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return FiniteIterable.of(entrySet()).map(entry -> entry.getKey() + ": " + entry.getValue()).join(Brackets.CURLY);
    }
    
}
