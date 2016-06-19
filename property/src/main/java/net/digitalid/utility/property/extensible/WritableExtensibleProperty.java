package net.digitalid.utility.property.extensible;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This writable property stores an extensible set of values.
 * 
 * @see VolatileWritableExtensibleProperty
 */
@Mutable
public abstract class WritableExtensibleProperty<V, R extends ReadOnlySet<@Nonnull V>> extends ExtensibleProperty<V, R> {
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    /**
     * Adds the given value to the values of this property.
     * 
     * @return whether the given value was not already stored.
     */
    @Impure
    public abstract boolean add(@Captured @Nonnull @Valid V value);
    
    /**
     * Removes the given value from the values of this property.
     * 
     * @return whether the given value was actually stored.
     */
    @Impure
    public abstract boolean remove(@Captured @Nonnull @Valid V value);
    
    /* -------------------------------------------------- Notifications -------------------------------------------------- */
    
    /**
     * Notifies the observers that the given value has been added.
     * 
     * @require get().contains(value) : "This property has to contain the value.";
     */
    @Impure
    protected void notifyAdded(@NonCaptured @Unmodified @Nonnull @Valid V value) {
        Require.that(get().contains(value)).orThrow("This property has to contain the value $ now.", value);
        
        if (hasObservers()) {
            for (ExtensibleProperty.@Nonnull Observer<V, R> observer : getObservers()) {
                observer.added(this, value);
            }
        }
    }
    
    /**
     * Notifies the observers that the given value has been removed.
     * 
     * @require !get().contains(value) : "This property may no longer contain the value.";
     */
    @Pure
    protected void notifyRemoved(@NonCaptured @Unmodified @Nonnull @Valid V value) {
        Require.that(!get().contains(value)).orThrow("This property may no longer contain the value $.", value);
        
        if (hasObservers()) {
            for (ExtensibleProperty.@Nonnull Observer<V, R> observer : getObservers()) {
                observer.removed(this, value);
            }
        }
    }
    
}
