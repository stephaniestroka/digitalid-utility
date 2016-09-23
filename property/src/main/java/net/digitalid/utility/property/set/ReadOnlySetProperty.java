package net.digitalid.utility.property.set;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.property.Property;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This read-only property stores a set of values.
 * 
 * @see WritableSetProperty
 * @see ReadOnlyVolatileSetProperty
 * @see ReadOnlySetPropertyImplementation
 */
@ReadOnly(WritableSetProperty.class)
public interface ReadOnlySetProperty<V, R extends ReadOnlySet<@Nonnull @Valid V>, X extends Exception, O extends ReadOnlySetProperty.Observer<V, R, X, O, P>, P extends ReadOnlySetProperty<V, R, X, O, P>> extends Property<O>, Valid.Value<V> {
    
    /* -------------------------------------------------- Observer -------------------------------------------------- */
    
    /**
     * Objects that implement this interface can be used to {@link #register(net.digitalid.utility.property.Property.Observer) observe} {@link ReadOnlySetProperty read-only set properties}.
     */
    @Mutable
    @Functional
    public static interface Observer<V, R extends ReadOnlySet<@Nonnull @Valid V>, X extends Exception, O extends ReadOnlySetProperty.Observer<V, R, X, O, P>, P extends ReadOnlySetProperty<V, R, X, O, P>> extends Property.Observer {
        
        /**
         * This method is called on {@link Property#isRegistered(net.digitalid.utility.property.Property.Observer) registered} observers when a value has been added to or removed from the given property.
         * 
         * @param added {@code true} if the given value has been added to or {@code false} if it has been removed from the given property.
         */
        @Impure
        public void notify(@Nonnull P property, @NonCaptured @Unmodified @Nonnull @Valid V value, boolean added);
        
    }
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    /**
     * Returns a read-only set with the values of this property.
     */
    @Pure
    public @Nonnull @NonFrozen R get() throws X;
    
}
