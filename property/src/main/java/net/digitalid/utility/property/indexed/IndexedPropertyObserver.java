package net.digitalid.utility.property.indexed;

import javax.annotation.Nonnull;

import net.digitalid.utility.collections.map.ReadOnlyMap;
import net.digitalid.utility.generator.annotations.GenerateNoBuilder;
import net.digitalid.utility.generator.annotations.GenerateNoSubclass;
import net.digitalid.utility.property.PropertyObserver;
import net.digitalid.utility.property.ReadOnlyProperty;

/**
 * Objects that implement this interface can be used to observe {@link ReadOnlyIndexedProperty indexed properties}.
 */
@GenerateNoBuilder
@GenerateNoSubclass
public interface IndexedPropertyObserver<K, V, R extends ReadOnlyMap<K, V>> extends PropertyObserver {
    
    /**
     * This method is called on {@link ReadOnlyProperty#register(PropertyObserver) registered} observers when an indexed value of the given property has been added.
     * 
     * @param property the property whose value has been added.
     * @param newKey the key of this property value that got added.
     * @param newValue the value of this property that got added.
     */
    public void added(@Nonnull ReadOnlyIndexedProperty<K, V, R> property, @Nonnull K newKey, @Nonnull V newValue);
    
    /**
     * This method is called on {@link ReadOnlyProperty#register(PropertyObserver) registered} observers when an indexed value of the given property has been removed.
     * 
     * @param property the property whose value has been removed.
     * @param oldKey the key of this property value that got removed.
     * @param oldValue the value of this property that got removed.
     */
    public void removed(@Nonnull ReadOnlyIndexedProperty<K, V, R> property, @Nonnull K oldKey, @Nonnull V oldValue);
    
}
