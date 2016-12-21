package net.digitalid.utility.property;

import net.digitalid.utility.property.map.MapObserver;
import net.digitalid.utility.property.set.SetObserver;
import net.digitalid.utility.property.value.ValueObserver;

/**
 * Objects that implement this interface can be used to {@link Property#register(net.digitalid.utility.property.Observer) observe} {@link Property properties}.
 * 
 * @see MapObserver
 * @see SetObserver
 * @see ValueObserver
 */
public interface Observer {}
