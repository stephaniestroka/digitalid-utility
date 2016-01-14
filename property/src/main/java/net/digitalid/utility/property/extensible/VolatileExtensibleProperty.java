package net.digitalid.utility.property.extensible;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.digitalid.utility.validation.state.Pure;
import net.digitalid.utility.validation.state.Validated;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.collections.freezable.FreezableSet;
import net.digitalid.utility.collections.readonly.ReadOnlySet;

/**
 * The property stores values in volatile memory.
 * 
 * <em>Important:</em> Make sure that {@code F} is a sub-type of {@code R}!
 * Unfortunately, this cannot be enforced with the limited Java generics.
 * 
 * [used for the hosts in the Server class and modules in the Service class]
 * 
 * @param <V> the type of the values.
 * @param <R> the type of the read-only set to which the set is casted to when retrieved with get().
 * @param <F> the type of the set that is used to store the values.
 */
public class VolatileExtensibleProperty<V, R extends ReadOnlySet<V>, F extends FreezableSet<V>> extends WritableExtensibleProperty<V, R, F> {
    
    /* -------------------------------------------------- Map -------------------------------------------------- */
    
    /**
     * A freezable set containing the values of this property.
     */
    private final @Nonnull @Validated F set;
    
    @Pure
    @Override
    @SuppressWarnings("unchecked")
    public @Nonnull @Validated @NonFrozen R get() {
        return (R) set;
    }
    
    /* -------------------------------------------------- Values -------------------------------------------------- */
    
    @Override
    public void add(@Nonnull V value) {
        boolean alreadyContained = set.add(value);
        
        if (!alreadyContained) { notifyAdded(set, value); }
    }
    
    @Override
    public void remove(@Nonnull V value) {
        boolean didExist = set.remove(value);
        
        if (didExist) { notifyRemoved(set, value); }
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new volatile extensible property with the given set.
     *  
     * @param set the set that holds the values.
     */
    private VolatileExtensibleProperty(@Nonnull @Validated F set) {
        this.set = set;
    }
    
    /**
     * Creates a new volatile extensible property with the given set.
     * 
     * @param set the set that stores the values of the property.
     * 
     * @return a new volatile indexed property object.
     */
    @Pure
    public static @Nullable <V, R extends ReadOnlySet<V>, F extends FreezableSet<V>> VolatileExtensibleProperty<V, R, F> get(@Nonnull @Validated F set) {
        return new VolatileExtensibleProperty<>(set);
    }

}
