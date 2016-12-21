package net.digitalid.utility.property.map;

import javax.annotation.Nonnull;

import net.digitalid.utility.collections.map.ReadOnlyMap;
import net.digitalid.utility.property.Property;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * Objects that implement this interface can be used to {@link Property#register(net.digitalid.utility.property.Observer) observe} {@link ReadOnlyVolatileMapProperty volatile map properties}.
 */
@Mutable
@Functional
public interface VolatileMapObserver<KEY, VALUE, READONLY_MAP extends ReadOnlyMap<@Nonnull @Valid("key") KEY, @Nonnull @Valid VALUE>> extends MapObserver<KEY, VALUE, READONLY_MAP, RuntimeException, VolatileMapObserver<KEY, VALUE, READONLY_MAP>, ReadOnlyVolatileMapProperty<KEY, VALUE, READONLY_MAP>> {}
