package net.digitalid.utility.property;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.collections.set.FreezableLinkedHashSet;
import net.digitalid.utility.collections.set.FreezableSet;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.property.extensible.ExtensibleProperty;
import net.digitalid.utility.property.indexed.IndexedProperty;
import net.digitalid.utility.property.nonnullable.NonNullableProperty;
import net.digitalid.utility.property.nullable.NullableProperty;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.value.Validated;

/**
 * A property is an object that can be {@link Property.Observer observed}.
 * 
 * @see NullableProperty
 * @see NonNullableProperty
 * @see ExtensibleProperty
 * @see IndexedProperty
 */
@Mutable
public abstract class Property<V, O extends Property.Observer<V>> extends RootClass implements Validated.Value<V> {
    
    /* -------------------------------------------------- Observer -------------------------------------------------- */
    
    /**
     * Objects that implement this interface can be used to observe {@link Property properties}.
     */
    public static interface Observer<V> {}
    
    /* -------------------------------------------------- Observers -------------------------------------------------- */
    
    /**
     * Stores null until the first observer registers and then the registered observers.
     */
    private @Nullable @NonFrozen FreezableSet<O> observers;
    
    /**
     * Registers the given observer for this property.
     * 
     * @return whether the given observer was not already registered.
     */
    @Impure
    public boolean register(@Nonnull O observer) {
        if (observers == null) { observers = FreezableLinkedHashSet.withCapacity(1); }
        return observers.add(observer);
    }
    
    /**
     * Deregisters the given observer for this property.
     * 
     * @return whether the given observer was actually registered.
     */
    @Impure
    public boolean deregister(@Nonnull O observer) {
        if (observers != null) { return observers.remove(observer); } else { return false; }
    }
    
    /**
     * Returns whether the given observer is registered for this property.
     */
    @Pure
    public boolean isRegistered(@Nonnull O observer) {
        return observers != null && observers.contains(observer);
    }
    
    /**
     * Returns whether this property has observers.
     */
    @Pure
    protected boolean hasObservers() {
        return observers != null && !observers.isEmpty();
    }
    
    /**
     * Returns the observers of this property.
     */
    @Pure
    protected @Nonnull @NonFrozen ReadOnlySet<O> getObservers() {
        if (observers == null) { observers = FreezableLinkedHashSet.withCapacity(1); }
        return observers;
    }
    
}
