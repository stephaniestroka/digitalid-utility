package net.digitalid.utility.property;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.digitalid.utility.property.extensible.ReadOnlyExtensibleProperty;
import net.digitalid.utility.property.indexed.ReadOnlyIndexedProperty;
import net.digitalid.utility.property.nonnullable.ReadOnlyNonNullableProperty;
import net.digitalid.utility.property.nullable.ReadOnlyNullableProperty;
import net.digitalid.utility.validation.state.Pure;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.collections.freezable.FreezableLinkedList;
import net.digitalid.utility.collections.freezable.FreezableList;
import net.digitalid.utility.collections.readonly.ReadOnlyList;
import net.digitalid.utility.logging.Log;

/**
 * A property is an object that can be {@link PropertyObserver observed}.
 * 
 * @see ReadOnlyNullableProperty
 * @see ReadOnlyNonNullableProperty
 * @see ReadOnlyExtensibleProperty
 * @see ReadOnlyIndexedProperty
 */
public abstract class ReadOnlyProperty<V, O extends PropertyObserver> {
    
    /* -------------------------------------------------- Observers -------------------------------------------------- */
    
    /**
     * Stores null until the first observer registers and then the list of observers.
     */
    private @Nullable @NonFrozen FreezableList<O> observers;
    
    /**
     * Registers the given observer for this property.
     * 
     * @param observer the observer to be registered.
     */
    public final void register(@Nonnull O observer) {
        if (observers == null) { observers = FreezableLinkedList.get(); }
        observers.add(observer);
    }
    
    /**
     * Deregisters the given observer for this property.
     * 
     * @param observer the observer to be deregistered.
     */
    public final void deregister(@Nonnull O observer) {
        if (observers != null) { observers.remove(observer); }
    }
    
    /**
     * Returns whether the given observer is registered for this property.
     * 
     * @param observer the observer to check whether it is registered.
     * 
     * @return whether the given observer is registered for this property.
     */
    @Pure
    public final boolean isRegistered(@Nonnull O observer) {
        return observers != null && observers.contains(observer);
    }
    
    /**
     * Returns whether this property has observers.
     * 
     * @return whether this property has observers.
     */
    @Pure
    protected final boolean hasObservers() {
        return observers != null && !observers.isEmpty();
    }
    
    /**
     * Returns the observers of this property.
     * 
     * @return the observers of this property.
     */
    @Pure
    protected final @Nonnull @NonFrozen ReadOnlyList<O> getObservers() {
        if (observers == null) { observers = FreezableLinkedList.get(); }
        return observers;
    }
    
}
