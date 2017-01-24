package net.digitalid.utility.property.map;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.collections.map.ReadOnlyMap;
import net.digitalid.utility.validation.annotations.type.ReadOnly;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This read-only property stores a map of key-value pairs in volatile memory.
 * 
 * @see WritableVolatileMapProperty
 */
@ThreadSafe
@ReadOnly(WritableVolatileMapProperty.class)
public interface ReadOnlyVolatileMapProperty<@Unspecifiable KEY, @Unspecifiable VALUE, @Unspecifiable READONLY_MAP extends ReadOnlyMap<@Nonnull @Valid("key") KEY, @Nonnull @Valid VALUE>> extends ReadOnlyMapProperty<KEY, VALUE, READONLY_MAP, RuntimeException, RuntimeException, VolatileMapObserver<KEY, VALUE, READONLY_MAP>, ReadOnlyVolatileMapProperty<KEY, VALUE, READONLY_MAP>> {}
