package net.digitalid.utility.property.set;

import javax.annotation.Nonnull;

import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.property.PropertyImplementation;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This class implements the {@link ReadOnlySetProperty}.
 * 
 * @see WritableSetPropertyImplementation
 */
@Mutable
public abstract class ReadOnlySetPropertyImplementation<V, R extends ReadOnlySet<@Nonnull @Valid V>, X extends Exception, O extends ReadOnlySetProperty.Observer<V, R, X, O, P>, P extends ReadOnlySetProperty<V, R, X, O, P>> extends PropertyImplementation<O> implements ReadOnlySetProperty<V, R, X, O, P> {}
