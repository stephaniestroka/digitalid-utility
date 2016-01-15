package net.digitalid.utility.property.extensible;

import javax.annotation.Nonnull;

import net.digitalid.utility.collections.readonly.ReadOnlySet;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.property.ReadOnlyProperty;
import net.digitalid.utility.property.ValueValidator;

/**
 * This is the read-only abstract class for extensible properties.
 * 
 * @see WritableExtensibleProperty
 */
public abstract class ReadOnlyExtensibleProperty<V, R extends ReadOnlySet<V>> extends ReadOnlyProperty<V, ExtensiblePropertyObserver<V, R>> {

    /* -------------------------------------------------- Constructor -------------------------------------------------- */

    /**
     * Creates a new read-only extensible property with the given validator.
     *
     * @param valueValidator the validator used to validate the values of this property.
     */
    protected ReadOnlyExtensibleProperty(@Nonnull ValueValidator<? super V> valueValidator) {
        super(valueValidator);
    }

    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    /**
     * Returns a read-only representation of the map.
     * 
     * @return a read-only representation of the map.
     */
    public abstract @Nonnull @NonFrozen R get();
    
}
