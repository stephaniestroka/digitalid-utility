package net.digitalid.utility.property;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.property.map.ReadOnlyMapProperty;
import net.digitalid.utility.property.set.ReadOnlySetProperty;
import net.digitalid.utility.property.value.ReadOnlyValueProperty;
import net.digitalid.utility.rootclass.RootInterface;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * A property is an object that can be {@link Property.Observer observed}.
 * 
 * @see ReadOnlyMapProperty
 * @see ReadOnlySetProperty
 * @see ReadOnlyValueProperty
 * @see PropertyImplementation
 */
@ReadOnly // This interface is mutable regarding the observers but read-only properties must be able to inherit from this interface.
@ThreadSafe
public interface Property<O extends Property.Observer> extends RootInterface {
    
    /* -------------------------------------------------- Observer -------------------------------------------------- */
    
    /**
     * Objects that implement this interface can be used to {@link #register(net.digitalid.utility.property.Property.Observer) observe} {@link Property properties}.
     */
    public static interface Observer {}
    
    /* -------------------------------------------------- Observers -------------------------------------------------- */
    
    /**
     * Registers the given observer with this property.
     * Use {@link #registerOnGuiThread(net.digitalid.utility.property.Property.Observer)} instead if the observer updates the GUI
     * or {@link #registerOnNewThread(net.digitalid.utility.property.Property.Observer)} if the update can take a lot of time.
     * 
     * @return whether the given observer was not already registered.
     */
    @Pure // This method is impure regarding the observers but read-only properties must be able to expose this method as well.
    public boolean register(@Captured @Nonnull O observer);
    
    /**
     * Registers the given observer with this property, which will then be notified on the GUI thread in the order of the updates.
     * 
     * @return whether the given observer was not already registered.
     */
    @Pure // This method is impure regarding the observers but read-only properties must be able to expose this method as well.
    public boolean registerOnGuiThread(@Captured @Nonnull O observer);
    
    /**
     * Registers the given observer with this property, which will then be notified on a separate thread in sequential order.
     * 
     * @return whether the given observer was not already registered.
     */
    @Pure // This method is impure regarding the observers but read-only properties must be able to expose this method as well.
    public boolean registerOnNewThread(@Captured @Nonnull O observer);
    
    /**
     * Deregisters the given observer with this property.
     * 
     * @return whether the given observer was actually registered.
     */
    @Pure // This method is impure regarding the observers but read-only properties must be able to expose this method as well.
    public boolean deregister(@NonCaptured @Nonnull O observer);
    
    /**
     * Returns whether the given observer is registered with this property.
     */
    @Pure
    public boolean isRegistered(@NonCaptured @Nonnull O observer);
    
}
