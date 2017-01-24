package net.digitalid.utility.property.set;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.property.Property;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * Objects that implement this interface can be used to {@link Property#register(net.digitalid.utility.property.Observer) observe} {@link ReadOnlyVolatileSetProperty volatile set properties}.
 */
@Mutable
@Functional
public interface VolatileSetObserver<@Unspecifiable VALUE, @Unspecifiable READONLY_SET extends ReadOnlySet<@Nonnull @Valid VALUE>> extends SetObserver<VALUE, READONLY_SET, RuntimeException, RuntimeException, VolatileSetObserver<VALUE, READONLY_SET>, ReadOnlyVolatileSetProperty<VALUE, READONLY_SET>> {}
