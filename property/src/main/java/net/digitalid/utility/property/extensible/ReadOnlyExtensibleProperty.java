package net.digitalid.utility.property.extensible;

import javax.annotation.Nonnull;

import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.collections.readonly.ReadOnlySet;
import net.digitalid.utility.property.ReadOnlyProperty;

/**
 * This is the read-only abstract class for extensible properties.
 * 
 * @see WritableExtensibleProperty
 */
public abstract class ReadOnlyExtensibleProperty<V, R extends ReadOnlySet<V>> extends ReadOnlyProperty<V, ExtensiblePropertyObserver<V, R>> {
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    /**
     * Returns a read-only representation of the map.
     * 
     * @return a read-only representation of the map.
     */
    public abstract @Nonnull @NonFrozen R get();
    
}
