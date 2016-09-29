package net.digitalid.utility.property.set;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.validation.annotations.type.ReadOnly;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This interface simplifies the declaration of {@link ReadOnlyVolatileSetProperty}.
 * 
 * @see WritableVolatileSimpleSetProperty
 */
@ThreadSafe
@ReadOnly(WritableVolatileSimpleSetProperty.class)
public interface ReadOnlyVolatileSimpleSetProperty<V> extends ReadOnlyVolatileSetProperty<V, ReadOnlySet<@Nonnull @Valid V>> {}
