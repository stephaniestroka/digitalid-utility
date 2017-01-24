package net.digitalid.utility.property.value;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.property.Property;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * Objects that implement this interface can be used to {@link Property#register(net.digitalid.utility.property.Observer) observe} {@link ReadOnlyVolatileValueProperty volatile value properties}.
 */
@Mutable
@Functional
public interface VolatileValueObserver<@Specifiable VALUE> extends ValueObserver<VALUE, RuntimeException, RuntimeException, VolatileValueObserver<VALUE>, ReadOnlyVolatileValueProperty<VALUE>> {}
