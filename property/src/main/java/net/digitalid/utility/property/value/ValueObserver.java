package net.digitalid.utility.property.value;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.property.Observer;
import net.digitalid.utility.property.Property;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * Objects that implement this interface can be used to {@link Property#register(net.digitalid.utility.property.Observer) observe} {@link ReadOnlyValueProperty value properties}.
 * 
 * @see VolatileValueObserver
 */
@Mutable
@Functional
public interface ValueObserver<@Specifiable VALUE, @Unspecifiable EXCEPTION1 extends Exception, @Unspecifiable EXCEPTION2 extends Exception, @Unspecifiable OBSERVER extends ValueObserver<VALUE, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY>, @Unspecifiable PROPERTY extends ReadOnlyValueProperty<VALUE, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY>> extends Observer {
    
    /**
     * This method is called on {@link Property#isRegistered(net.digitalid.utility.property.Observer) registered} observers when the value of the given property has been replaced.
     * 
     * @require !Objects.equals(newValue, oldValue) : "The new value may not be the same as the old value.";
     */
    @Impure
    public void notify(@Nonnull PROPERTY property, @NonCaptured @Unmodified @Valid VALUE oldValue, @NonCaptured @Unmodified @Valid VALUE newValue);
    
}
