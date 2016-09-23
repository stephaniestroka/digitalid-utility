package net.digitalid.utility.property;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.property.map.ReadOnlyMapProperty;
import net.digitalid.utility.property.set.ReadOnlySetProperty;
import net.digitalid.utility.property.value.ReadOnlyValueProperty;
import net.digitalid.utility.rootclass.RootInterface;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * A property is an object that can be {@link Property.Observer observed}.
 * 
 * @see ReadOnlyMapProperty
 * @see ReadOnlySetProperty
 * @see ReadOnlyValueProperty
 * @see PropertyImplementation
 */
@Mutable
public interface Property<O extends Property.Observer> extends RootInterface {
    
    /* -------------------------------------------------- Observer -------------------------------------------------- */
    
    /**
     * Objects that implement this interface can be used to {@link #register(net.digitalid.utility.property.Property.Observer) observe} {@link Property properties}.
     */
    public static interface Observer {}
    
    /* -------------------------------------------------- Observers -------------------------------------------------- */
    
    /**
     * Registers the given observer for this property.
     * 
     * @return whether the given observer was not already registered.
     */
    @Pure // 
    public boolean register(@Captured @Nonnull O observer);
    
    /**
     * Deregisters the given observer for this property.
     * 
     * @return whether the given observer was actually registered.
     */
    @Pure
    public boolean deregister(@NonCaptured @Nonnull O observer);
    
    /**
     * Returns whether the given observer is registered for this property.
     */
    @Pure
    public boolean isRegistered(@NonCaptured @Nonnull O observer);
    
}
