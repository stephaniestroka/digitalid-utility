package net.digitalid.utility.collections.readonly;

import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.digitalid.utility.validation.reference.Capturable;
import net.digitalid.utility.validation.state.Immutable;
import net.digitalid.utility.validation.state.Pure;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.freezable.Freezable;
import net.digitalid.utility.collections.freezable.FreezableMap;
import net.digitalid.utility.readonly.ReadOnly;

/**
 * This interface provides read-only access to {@link Map maps} and should <em>never</em> be cast away (unless external code requires it).
 * Please note that {@link Map#entrySet()} cannot be supported because it is not possible to return a covariant generic type.
 * <p>
 * <em>Important:</em> Only use immutable types for the keys and freezable or immutable types for the values!
 * (The types are not restricted to {@link Freezable} or {@link Immutable} so that library types can also be used.)
 * 
 * @see FreezableMap
 */
public interface ReadOnlyMap<K,V> extends ReadOnly {
    
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
    public boolean containsKey(@Nullable Object key);
    
    /**
     * @see Map#containsValue(java.lang.Object)
     */
    @Pure
    public boolean containsValue(@Nullable Object value);
    
    /**
     * @see Map#get(java.lang.Object)
     */
    @Pure
    public @Nullable V get(@Nullable Object key);
    
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
