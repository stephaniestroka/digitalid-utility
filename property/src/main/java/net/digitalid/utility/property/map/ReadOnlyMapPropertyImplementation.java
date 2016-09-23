package net.digitalid.utility.property.map;

import javax.annotation.Nonnull;

import net.digitalid.utility.collections.map.ReadOnlyMap;
import net.digitalid.utility.property.PropertyImplementation;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This class implements the {@link ReadOnlyMapProperty}.
 * 
 * @see WritableMapPropertyImplementation
 */
@Mutable
public abstract class ReadOnlyMapPropertyImplementation<K, V, R extends ReadOnlyMap<@Nonnull @Valid("key") K, @Nonnull @Valid V>, X extends Exception, O extends ReadOnlyMapProperty.Observer<K, V, R, X, O, P>, P extends ReadOnlyMapProperty<K, V, R, X, O, P>> extends PropertyImplementation<O> implements ReadOnlyMapProperty<K, V, R, X, O, P> {}
