package net.digitalid.utility.immutable.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import net.digitalid.utility.immutable.iterators.ImmutableIterator;

/**
 * This class implements an immutable map of non-nullable elements.
 * <p>
 * <em>Important:</em> This map is only immutable in Java 1.7!
 */
public class ImmutableMap<K, V> extends LinkedHashMap<K, V> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */

    protected ImmutableMap(Map<? extends K, ? extends V> map) {
        super(map);
        
        if (containsKey(null) || containsValue(null)) { throw new NullPointerException("An immutable map may not contain null."); }
    }
    
    /**
     * Returns an immutable map with the elements of the given map.
     * The given map is not captured as its keys and values are copied to the immutable map.
     * 
     * @throws NullPointerException if any of the elements of the given map is null.
     */
    public static <K, V> ImmutableMap<K, V> with(Map<? extends K, ? extends V> map) {
        return new ImmutableMap<>(map);
    }
    
    /* -------------------------------------------------- NonNullable Results -------------------------------------------------- */
    
    /**
     * {@inheritDoc}
     *
     * @ensure result != null : "The result may not be null.";
     */
    @Override
    public V get(Object key) {
        return super.get(key);
    }
    
    /* -------------------------------------------------- Modified Operations -------------------------------------------------- */
    
    @Override
    public final ImmutableSet<K> keySet() {
        // Copies the keys of this immutable map, which leads to some overhead but also prevents memory leaks.
        return ImmutableSet.with(super.keySet());
    }
    
    @Override
    public final ImmutableList<V> values() {
        // Copies the values of this immutable map, which leads to some overhead but also prevents memory leaks.
        return ImmutableList.with(super.values());
    }
    
    /* -------------------------------------------------- Entry Set -------------------------------------------------- */
    
    private static class ImmutableEntry<K, V> implements Map.Entry<K, V> {
        
        protected final Map.Entry<K, V> entry;
        
        protected ImmutableEntry(Map.Entry<K, V> entry) {
            this.entry = entry;
        }
        
        @Override
        public K getKey() {
            return entry.getKey();
        }
        
        @Override
        public V getValue() {
            return entry.getValue();
        }
        
        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
        public boolean equals(Object o) {
            return entry.equals(o);
        }
        
        @Override
        public int hashCode() {
            return entry.hashCode();
        }
        
    }
    
    private static class ImmutableEntrySetIterator<K, V> extends ImmutableIterator<Map.Entry<K, V>> {
        
        protected ImmutableEntrySetIterator(Iterator<Map.Entry<K, V>> iterator) {
            super(iterator);
        }
        
        @Override
        public Map.Entry<K, V> next() {
            return new ImmutableEntry<>(super.next());
        }
        
    }
    
    private static class ImmutableEntrySet<K, V> extends ImmutableSet<Map.Entry<K, V>> {
        
        protected ImmutableEntrySet(Collection<Map.Entry<K, V>> collection) {
            super(collection);
        }
        
        @Override
        public ImmutableIterator<Map.Entry<K, V>> iterator() {
            return new ImmutableEntrySetIterator<>(super.iterator());
        }
        
    }
    
    @Override
    public final ImmutableSet<Map.Entry<K, V>> entrySet() {
        // Copies the values of this immutable map, which leads to some overhead but also prevents memory leaks.
        return new ImmutableEntrySet<>(super.entrySet());
    }
    
    /* -------------------------------------------------- Unsupported Operations -------------------------------------------------- */
    
    @Override
    public final V put(K key, V value) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final V remove(Object key) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final void clear() {
        throw new UnsupportedOperationException();
    }
    
    /* -------------------------------------------------- Builder -------------------------------------------------- */
    
    /**
     * This class implements a builder for the immutable map.
     */
    public static class ImmutableMapBuilder<K, V> extends LinkedHashMap<K, V> {
        
        /**
         * Adds the given key-value pair to this builder and returns itself.
         */
        public ImmutableMapBuilder<K, V> with(K key, V value) {
            put(Objects.requireNonNull(key), Objects.requireNonNull(value));
            return this;
        }
        
        /**
         * Returns an immutable map with the key-value pairs of this builder.
         */
        public ImmutableMap<K, V> build() {
            return ImmutableMap.with(this);
        }
        
    }
    
    /**
     * Returns an immutable map builder with the given key-value pair.
     * 
     * @throws NullPointerException if the given key or value is null.
     */
    public static <K, V> ImmutableMapBuilder<K, V> with(K key, V value) {
        return new ImmutableMapBuilder<K, V>().with(key, value);
    }
    
    /**
     * Returns an immutable map with no entries in it.
     */
    public static <K, V> ImmutableMap<K, V> withNoEntries() {
        return new ImmutableMapBuilder<K, V>().build();
    }
    
}
