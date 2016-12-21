package net.digitalid.utility.property.set;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.property.Observer;
import net.digitalid.utility.property.Property;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * Objects that implement this interface can be used to {@link Property#register(net.digitalid.utility.property.Observer) observe} {@link ReadOnlySetProperty set properties}.
 * 
 * @see VolatileSetObserver
 */
@Mutable
@Functional
public interface SetObserver<VALUE, READONLY_SET extends ReadOnlySet<@Nonnull @Valid VALUE>, EXCEPTION extends Exception, OBSERVER extends SetObserver<VALUE, READONLY_SET, EXCEPTION, OBSERVER, PROPERTY>, PROPERTY extends ReadOnlySetProperty<VALUE, READONLY_SET, EXCEPTION, OBSERVER, PROPERTY>> extends Observer {
    
    /**
     * This method is called on {@link Property#isRegistered(net.digitalid.utility.property.Observer) registered} observers when a value has been added to or removed from the given property.
     * 
     * @param added {@code true} if the given value has been added to or {@code false} if it has been removed from the given property.
     */
    @Impure
    public void notify(@Nonnull PROPERTY property, @NonCaptured @Unmodified @Nonnull @Valid VALUE value, boolean added);
    
}
