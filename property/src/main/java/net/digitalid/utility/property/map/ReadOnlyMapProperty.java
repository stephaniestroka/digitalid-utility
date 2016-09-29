package net.digitalid.utility.property.map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.collaboration.enumerations.Priority;
import net.digitalid.utility.collections.map.ReadOnlyMap;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.property.Property;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This read-only property stores a map of key-value pairs.
 * 
 * @see WritableMapProperty
 * @see ReadOnlyVolatileMapProperty
 * @see ReadOnlyMapPropertyImplementation
 */
@ThreadSafe
@ReadOnly(WritableMapProperty.class)
@TODO(task = "Restrict the ReadOnlyMap to a SyncrhonizedReadOnlyMap.", date = "2016-09-27", author = Author.KASPAR_ETTER, assignee = Author.KASPAR_ETTER, priority = Priority.MIDDLE)
public interface ReadOnlyMapProperty<K, V, R extends ReadOnlyMap<@Nonnull @Valid("key") K, @Nonnull @Valid V>, X extends Exception, O extends ReadOnlyMapProperty.Observer<K, V, R, X, O, P>, P extends ReadOnlyMapProperty<K, V, R, X, O, P>> extends Property<O>, Valid.Key<K>, Valid.Value<V> {
    
    /* -------------------------------------------------- Observer -------------------------------------------------- */
    
    /**
     * Objects that implement this interface can be used to {@link #register(net.digitalid.utility.property.Property.Observer) observe} {@link ReadOnlyMapProperty read-only set properties}.
     */
    @Mutable
    public static interface Observer<K, V, R extends ReadOnlyMap<@Nonnull @Valid("key") K, @Nonnull @Valid V>, X extends Exception, O extends ReadOnlyMapProperty.Observer<K, V, R, X, O, P>, P extends ReadOnlyMapProperty<K, V, R, X, O, P>> extends Property.Observer {
        
        /**
         * This method is called on {@link Property#isRegistered(net.digitalid.utility.property.Property.Observer) registered} observers when a key-value pair has been added to or removed from the given property.
         * 
         * @param added {@code true} if the given key-value pair has been added to or {@code false} if it has been removed from the given property.
         */
        @Impure
        public void notify(@Nonnull P property, @NonCaptured @Unmodified @Nonnull @Valid("key") K key, @NonCaptured @Unmodified @Nonnull @Valid V value, boolean added);
        
    }
    
    /* -------------------------------------------------- Getters -------------------------------------------------- */
    
    /**
     * Returns the value for the given key.
     */
    @Pure
    public @NonCapturable @Nullable @Valid V get(@NonCaptured @Unmodified @Nonnull @Valid("key") K key);
    
    /**
     * Returns a read-only map with the key-value pairs of this property.
     */
    @Pure
    public @Nonnull @NonFrozen R get() throws X;
    
}
