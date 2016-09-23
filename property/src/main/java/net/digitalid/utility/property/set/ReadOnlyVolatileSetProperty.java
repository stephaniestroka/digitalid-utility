package net.digitalid.utility.property.set;

import javax.annotation.Nonnull;

import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This read-only property stores a set of values in volatile memory.
 * 
 * @see WritableVolatileSetProperty
 * @see ReadOnlyVolatileSimpleSetProperty
 */
@ReadOnly(WritableVolatileSetProperty.class)
public interface ReadOnlyVolatileSetProperty<V, R extends ReadOnlySet<@Nonnull @Valid V>> extends ReadOnlySetProperty<V, R, RuntimeException, ReadOnlyVolatileSetProperty.Observer<V, R>, ReadOnlyVolatileSetProperty<V, R>> {
    
    /* -------------------------------------------------- Observer -------------------------------------------------- */
    
    /**
     * Objects that implement this interface can be used to {@link #register(net.digitalid.utility.property.Property.Observer) observe} {@link ReadOnlyVolatileSetProperty read-only volatile set properties}.
     */
    @Mutable
    @Functional
    public static interface Observer<V, R extends ReadOnlySet<@Nonnull @Valid V>> extends ReadOnlySetProperty.Observer<V, R, RuntimeException, ReadOnlyVolatileSetProperty.Observer<V, R>, ReadOnlyVolatileSetProperty<V, R>> {}
    
}
