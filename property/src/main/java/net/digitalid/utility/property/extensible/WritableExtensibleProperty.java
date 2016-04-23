package net.digitalid.utility.property.extensible;

import javax.annotation.Nonnull;

import net.digitalid.utility.collections.set.FreezableSet;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.generator.annotations.GenerateNoBuilder;
import net.digitalid.utility.generator.annotations.GenerateNoSubclass;
import net.digitalid.utility.property.Validated;
import net.digitalid.utility.property.ValueValidator;

/**
 * This is the writable abstract class for properties that stores a set of values.
 * 
 * <em>Important:</em> Make sure that {@code F} is a subtype of {@code R}!
 * Unfortunately, this cannot be enforced with the limited Java generics.
 * 
 * [used for the hosts in the Server class and modules in the Service class]
 * 
 * @see VolatileExtensibleProperty
 */
@GenerateNoBuilder
@GenerateNoSubclass
public abstract class WritableExtensibleProperty<V, R extends ReadOnlySet<V>, F extends FreezableSet<V>> extends ReadOnlyExtensibleProperty<V, R> {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */

    /**
     * Creates a new writable extensible property with the given validators.
     *
     * @param valueValidator the validator used to validate the value of this property.
     */
    protected WritableExtensibleProperty(@Nonnull ValueValidator<? super V> valueValidator) {
        super(valueValidator);
    }

    /* -------------------------------------------------- Modifiers -------------------------------------------------- */
    
    /**
     * Adds a new value to the set.
     *
     * @param value the value of this property that got added.
     */
    public abstract void add(@Nonnull @Frozen V value);
    
    /**
     * Removes a value from the set.
     *
     * @param value the value of this property that got removed.
     */
    public abstract void remove(@Nonnull @Frozen V value);
    
    /* -------------------------------------------------- Notification -------------------------------------------------- */
    
    /**
     * Notifies the observers that a value was added.
     * 
     * @param value the value of this property that got added.
     * 
     * @require set.contains(value) : "The value does not exist in the set.";
     */
    protected final void notifyAdded(@Nonnull F set, @Nonnull @Validated V value) {
        Require.that(set.contains(value)).orThrow("The value does not exist in the set.");
        
        if (hasObservers()) {
            for (final @Nonnull ExtensiblePropertyObserver<V, R> observer : getObservers()) {
                observer.added(this, value);
            }
        }
    }
    
    /**
     * Notifies the observers that value was removed.
     * 
     * @param value the value of this property that got removed.
     * 
     * @require set.contains(value) : "The value exists in the set.";
     */
    protected final void notifyRemoved(@Nonnull F set, @Nonnull @Validated V value) {
        Require.that(set.contains(value)).orThrow("The value exists in the set.");
        
        if (hasObservers()) {
            for (final @Nonnull ExtensiblePropertyObserver<V, R> observer : getObservers()) {
                observer.removed(this, value);
            }
        }
    }
    
}
