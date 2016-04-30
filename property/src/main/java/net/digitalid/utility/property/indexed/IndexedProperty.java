package net.digitalid.utility.property.indexed;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.collections.map.ReadOnlyMap;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.property.Property;
import net.digitalid.utility.validation.annotations.getter.Default;
import net.digitalid.utility.validation.annotations.value.Validated;

/**
 * This read-only property stores indexed values.
 * 
 * @see WritableIndexedProperty
 */
@Mutable
public abstract class IndexedProperty<K, V, R extends ReadOnlyMap<@Nonnull K, @Nonnull V>> extends Property<V, IndexedProperty.Observer<K, V, R>> {
    
    /* -------------------------------------------------- Observer -------------------------------------------------- */
    
    /**
     * Objects that implement this interface can be used to observe {@link IndexedProperty indexed properties}.
     */
    @Mutable
    public static interface Observer<K, V, R extends ReadOnlyMap<K, V>> extends Property.Observer<V> {
        
        /**
         * This method is called on {@link Property#isRegistered(net.digitalid.utility.property.Property.Observer) registered} observers when an indexed value of the given property has been added.
         * 
         * @require getKeyValidator().evaluate(key) : "The key has to be valid.";
         */
        public void added(@NonCaptured @Unmodified @Nonnull IndexedProperty<K, V, R> property, @NonCaptured @Unmodified @Nonnull K key, @NonCaptured @Unmodified @Nonnull @Validated V value);
        
        /**
         * This method is called on {@link Property#isRegistered(net.digitalid.utility.property.Property.Observer) registered} observers when an indexed value of the given property has been removed.
         * 
         * @require getKeyValidator().evaluate(key) : "The key has to be valid.";
         */
        public void removed(@NonCaptured @Unmodified @Nonnull IndexedProperty<K, V, R> property, @NonCaptured @Unmodified @Nonnull K key, @NonCaptured @Unmodified @Nonnull @Validated V value);
        
    }
    
    /* -------------------------------------------------- Key Validator -------------------------------------------------- */
    
    /**
     * Returns the validator which validates the keys of this property.
     */
    @Pure
    @Default("object -> true")
    public abstract @Nonnull Predicate<? super K> getKeyValidator();
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    /**
     * Returns the value for the given key.
     * 
     * @require getKeyValidator().evaluate(key) : "The key has to be valid.";
     */
    @Pure
    public abstract @NonCapturable @Nullable @Validated V get(@NonCaptured @Unmodified @Nonnull K key);
    
    /**
     * Returns a read-only view of the indexed values.
     */
    @Pure
    public abstract @NonCapturable @Nonnull @NonFrozen R getMap();
    
}
