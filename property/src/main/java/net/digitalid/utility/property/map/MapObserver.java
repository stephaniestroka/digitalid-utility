package net.digitalid.utility.property.map;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.collections.map.ReadOnlyMap;
import net.digitalid.utility.property.Observer;
import net.digitalid.utility.property.Property;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * Objects that implement this interface can be used to {@link Property#register(net.digitalid.utility.property.Observer) observe} {@link ReadOnlyMapProperty map properties}.
 * 
 * @see VolatileMapObserver
 */
@Mutable
public interface MapObserver<@Unspecifiable KEY, @Unspecifiable VALUE, @Unspecifiable READONLY_MAP extends ReadOnlyMap<@Nonnull @Valid("key") KEY, @Nonnull @Valid VALUE>, @Unspecifiable EXCEPTION1 extends Exception, @Unspecifiable EXCEPTION2 extends Exception, @Unspecifiable OBSERVER extends MapObserver<KEY, VALUE, READONLY_MAP, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY>, @Unspecifiable PROPERTY extends ReadOnlyMapProperty<KEY, VALUE, READONLY_MAP, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY>> extends Observer {
    
    /**
     * This method is called on {@link Property#isRegistered(net.digitalid.utility.property.Observer) registered} observers when a key-value pair has been added to or removed from the given property.
     * 
     * @param added {@code true} if the given key-value pair has been added to or {@code false} if it has been removed from the given property.
     */
    @Impure
    public void notify(@Nonnull PROPERTY property, @NonCaptured @Unmodified @Nonnull @Valid("key") KEY key, @NonCaptured @Unmodified @Nonnull @Valid VALUE value, boolean added);
    
}
