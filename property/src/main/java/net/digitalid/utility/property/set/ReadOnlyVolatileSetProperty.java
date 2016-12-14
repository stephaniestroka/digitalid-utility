package net.digitalid.utility.property.set;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.validation.annotations.type.ReadOnly;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This read-only property stores a set of values in volatile memory.
 * 
 * @see WritableVolatileSetProperty
 * @see ReadOnlyVolatileSimpleSetProperty
 */
@ThreadSafe
@ReadOnly(WritableVolatileSetProperty.class)
public interface ReadOnlyVolatileSetProperty<VALUE, READONLY_SET extends ReadOnlySet<@Nonnull @Valid VALUE>> extends ReadOnlySetProperty<VALUE, READONLY_SET, RuntimeException, VolatileSetObserver<VALUE, READONLY_SET>, ReadOnlyVolatileSetProperty<VALUE, READONLY_SET>> {}
