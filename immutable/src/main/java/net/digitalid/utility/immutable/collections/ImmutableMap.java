package net.digitalid.utility.immutable.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.immutable.iterators.ImmutableIterator;
import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class implements an immutable map.
 */
@Immutable
public class ImmutableMap<K, V> extends LinkedHashMap<K, V> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ImmutableMap(@NonCaptured @Unmodified @Nonnull Map<? extends K, ? extends V> map) {
        super(map);
    }
    
    /**
     * Returns an immutable map with the elements of the given map.
     * The given map is not captured as its keys and values are copied to the immutable map.
     */
    @Pure
    public static <K, V> @Nonnull ImmutableMap<K, V> with(@NonCaptured @Unmodified @Nonnull Map<? extends K, ? extends V> map) {
        return new ImmutableMap<>(map);
    }
    
    /* -------------------------------------------------- Modified Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public final @Nonnull ImmutableSet<K> keySet() {
        // Copies the keys of this immutable map, which leads to some overhead but also prevents memory leaks.
        return ImmutableSet.with(super.keySet());
    }
    
    @Pure
    @Override
    public final @Nonnull ImmutableList<V> values() {
        // Copies the values of this immutable map, which leads to some overhead but also prevents memory leaks.
        return ImmutableList.with(super.values());
    }
    
    /* -------------------------------------------------- Entry Set -------------------------------------------------- */
    
    @Immutable
    public static class ImmutableEntry<K, V> implements Map.Entry<K, V> {
        
        protected final Map.@Nonnull Entry<K, V> entry;
        
        protected ImmutableEntry(@Captured Map.@Nonnull Entry<K, V> entry) {
            this.entry = entry;
        }
        
        @Pure
        @Override
        public K getKey() {
            return entry.getKey();
        }
        
        @Pure
        @Override
        public V getValue() {
            return entry.getValue();
        }
        
        @Pure
        @Override
        public V setValue(@NonCaptured @Unmodified V value) {
            throw new UnsupportedOperationException();
        }
        
        @Pure
        @Override
        @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
        public boolean equals(@NonCaptured @Unmodified @Nullable Object object) {
            return entry.equals(object);
        }
        
        @Pure
        @Override
        public int hashCode() {
            return entry.hashCode();
        }
        
    }
    
    @Mutable
    public static class ImmutableEntrySetIterator<K, V> extends ImmutableIterator<Map.@Nonnull Entry<K, V>> {
        
        protected ImmutableEntrySetIterator(@Captured @Nonnull Iterator<Map.Entry<K, V>> iterator) {
            super(iterator);
        }
        
        @Impure
        @Override
        public @Nonnull ImmutableEntry<K, V> next() {
            return new ImmutableEntry<>(super.next());
        }
        
    }
    
    @Immutable
    public static class ImmutableEntrySet<K, V> extends ImmutableSet<Map.@Nonnull Entry<K, V>> {
        
        protected ImmutableEntrySet(@NonCaptured @Unmodified @Nonnull Collection<Map.Entry<K, V>> collection) {
            super(collection);
        }
        
        @Pure
        @Override
        public @Capturable @Nonnull ImmutableEntrySetIterator<K, V> iterator() {
            return new ImmutableEntrySetIterator<>(super.iterator());
        }
        
    }
    
    @Pure
    @Override
    public final @Nonnull ImmutableEntrySet<K, V> entrySet() {
        // Copies the values of this immutable map, which leads to some overhead but also prevents memory leaks.
        return new ImmutableEntrySet<>(super.entrySet());
    }
    
    /* -------------------------------------------------- Unsupported Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public final @NonCapturable V put(@NonCaptured @Unmodified K key, @NonCaptured @Unmodified V value) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final @NonCapturable V remove(@NonCaptured @Unmodified Object key) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final void putAll(@NonCaptured @Unmodified Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final void clear() {
        throw new UnsupportedOperationException();
    }
    
    /* -------------------------------------------------- Builder -------------------------------------------------- */
    
    /**
     * This class implements a builder for the immutable map.
     */
    @Mutable
    public static class ImmutableMapBuilder<K, V> extends LinkedHashMap<K, V> {
        
        /**
         * Adds the given key-value pair to this builder and returns itself.
         */
        @Impure
        @Chainable
        public @NonCapturable @Nonnull ImmutableMapBuilder<K, V> with(@Captured K key, @Captured V value) {
            put(key, value);
            return this;
        }
        
        /**
         * Returns an immutable map with the key-value pairs of this builder.
         */
        @Pure
        public @Nonnull ImmutableMap<K, V> build() {
            return ImmutableMap.with(this);
        }
        
    }
    
    /**
     * Returns an immutable map builder with the given key-value pair.
     */
    @Pure
    public static <K, V> @Capturable @Nonnull ImmutableMapBuilder<K, V> with(@Captured K key, @Captured V value) {
        return new ImmutableMapBuilder<K, V>().with(key, value);
    }
    
    /**
     * Returns an immutable map with no entries in it.
     */
    @Pure
    public static <K, V> @Nonnull ImmutableMap<K, V> withNoEntries() {
        return new ImmutableMapBuilder<K, V>().build();
    }
    
}
