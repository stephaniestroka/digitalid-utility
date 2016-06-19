package net.digitalid.utility.property.extensible;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.property.Property;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This read-only property stores an extensible set of values.
 * 
 * @see WritableExtensibleProperty
 */
@Mutable
public abstract class ExtensibleProperty<V, R extends ReadOnlySet<@Nonnull V>> extends Property<V, ExtensibleProperty.Observer<V, R>> {
    
    /* -------------------------------------------------- Observer -------------------------------------------------- */
    
    /**
     * Objects that implement this interface can be used to observe {@link ExtensibleProperty extensible properties}.
     */
    @Mutable
    public static interface Observer<V, R extends ReadOnlySet<@Nonnull V>> extends Property.Observer<V> {
        
        /**
         * This method is called on {@link Property#isRegistered(net.digitalid.utility.property.Property.Observer) registered} observers when a value of the given extensible property has been added.
         */
        @Impure
        public void added(@NonCaptured @Unmodified @Nonnull ExtensibleProperty<V, R> property, @NonCaptured @Unmodified @Nonnull @Valid V value);
        
        /**
         * This method is called on {@link Property#isRegistered(net.digitalid.utility.property.Property.Observer) registered} observers when a value of the given extensible property has been removed.
         */
        @Impure
        public void removed(@NonCaptured @Unmodified @Nonnull ExtensibleProperty<V, R> property, @NonCaptured @Unmodified @Nonnull @Valid V value);
        
    }
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    /**
     * Returns a read-only view of the stored values.
     */
    @Pure
    public abstract @NonCapturable @Nonnull @NonFrozen R get();
    
}
