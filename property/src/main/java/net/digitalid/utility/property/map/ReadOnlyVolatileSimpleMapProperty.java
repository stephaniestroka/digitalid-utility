package net.digitalid.utility.property.map;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.collections.map.ReadOnlyMap;
import net.digitalid.utility.validation.annotations.type.ReadOnly;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This interface simplifies the declaration of {@link ReadOnlyVolatileMapProperty}.
 * 
 * @see WritableVolatileSimpleMapProperty
 */
@ThreadSafe
@ReadOnly(WritableVolatileSimpleMapProperty.class)
public interface ReadOnlyVolatileSimpleMapProperty<K, V> extends ReadOnlyVolatileMapProperty<K, V, ReadOnlyMap<@Nonnull @Valid("key") K, @Nonnull @Valid V>> {}
