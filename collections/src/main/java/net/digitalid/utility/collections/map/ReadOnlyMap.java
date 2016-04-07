package net.digitalid.utility.collections.map;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.collections.collection.ReadOnlyCollection;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.freezable.ReadOnlyInterface;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This interface provides read-only access to {@link Map maps} and should <em>never</em> be cast away (unless external code requires it).
 * Please note that {@link Map#entrySet()} cannot be supported because it is not possible to return a covariant generic type.
 * It is recommended to use only {@link Freezable} or {@link Immutable} types for the elements.
 * 
 * @see FreezableMap
 */
@ReadOnly(FreezableMap.class)
public interface ReadOnlyMap<K,V> extends ReadOnlyInterface {
    
    /* -------------------------------------------------- Map -------------------------------------------------- */
    
    /**
     * @see Map#size()
     */
    @Pure
    public int size();
    
    /**
     * @see Map#isEmpty()
     */
    @Pure
    public boolean isEmpty();
    
    /**
     * @see Map#containsKey(java.lang.Object)
     */
    @Pure
    public boolean containsKey(@NonCaptured @Unmodified @Nullable Object key);
    
    /**
     * @see Map#containsValue(java.lang.Object)
     */
    @Pure
    public boolean containsValue(@NonCaptured @Unmodified @Nullable Object value);
    
    /**
     * @see Map#get(java.lang.Object)
     */
    @Pure
    public @NonCapturable @Nullable V get(@NonCaptured @Unmodified @Nullable Object key);
    
    /**
     * @see Map#keySet()
     */
    @Pure
    public @Nonnull ReadOnlySet<K> keySet();
    
    /**
     * @see Map#values()
     */
    @Pure
    public @Nonnull ReadOnlyCollection<V> values();
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableMap<K,V> clone();
    
}
