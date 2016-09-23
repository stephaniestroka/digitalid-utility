package net.digitalid.utility.property.map;

import javax.annotation.Nonnull;

import net.digitalid.utility.collections.map.ReadOnlyMap;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This read-only property stores a map of key-value pairs in volatile memory.
 * 
 * @see WritableVolatileMapProperty
 */
@ReadOnly(WritableVolatileMapProperty.class)
public interface ReadOnlyVolatileMapProperty<K, V, R extends ReadOnlyMap<@Nonnull @Valid("key") K, @Nonnull @Valid V>> extends ReadOnlyMapProperty<K, V, R, RuntimeException, ReadOnlyVolatileMapProperty.Observer<K, V, R>, ReadOnlyVolatileMapProperty<K, V, R>> {
    
    /* -------------------------------------------------- Observer -------------------------------------------------- */
    
    /**
     * Objects that implement this interface can be used to {@link #register(net.digitalid.utility.property.Property.Observer) observe} {@link ReadOnlyVolatileMapProperty read-only volatile map properties}.
     */
    @Mutable
    @Functional
    public static interface Observer<K, V, R extends ReadOnlyMap<@Nonnull @Valid("key") K, @Nonnull @Valid V>> extends ReadOnlyMapProperty.Observer<K, V, R, RuntimeException, ReadOnlyVolatileMapProperty.Observer<K, V, R>, ReadOnlyVolatileMapProperty<K, V, R>> {}
    
}
