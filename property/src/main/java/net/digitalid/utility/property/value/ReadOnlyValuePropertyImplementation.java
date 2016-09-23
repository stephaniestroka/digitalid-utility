package net.digitalid.utility.property.value;

import net.digitalid.utility.property.PropertyImplementation;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements the {@link ReadOnlyValueProperty}.
 * 
 * @see WritableValuePropertyImplementation
 */
@Mutable
public abstract class ReadOnlyValuePropertyImplementation<V, X extends Exception, O extends ReadOnlyValueProperty.Observer<V, X, O, P>, P extends ReadOnlyValueProperty<V, X, O, P>> extends PropertyImplementation<O> implements ReadOnlyValueProperty<V, X, O, P> {}
