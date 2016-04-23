package net.digitalid.utility.property;

import net.digitalid.utility.generator.annotations.GenerateNoBuilder;
import net.digitalid.utility.generator.annotations.GenerateNoSubclass;
import net.digitalid.utility.property.extensible.ExtensiblePropertyObserver;
import net.digitalid.utility.property.indexed.IndexedPropertyObserver;
import net.digitalid.utility.property.nonnullable.NonNullablePropertyObserver;
import net.digitalid.utility.property.nullable.NullablePropertyObserver;

/**
 * Objects that implement this interface can be used to observe {@link ReadOnlyProperty properties}.
 * 
 * @see IndexedPropertyObserver
 * @see ExtensiblePropertyObserver
 * @see NullablePropertyObserver
 * @see NonNullablePropertyObserver
 */
@GenerateNoBuilder
@GenerateNoSubclass
public interface PropertyObserver {}
