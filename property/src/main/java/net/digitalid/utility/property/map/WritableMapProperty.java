package net.digitalid.utility.property.map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.collections.map.ReadOnlyMap;
import net.digitalid.utility.concurrency.exceptions.ReentranceException;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This writable property stores a map of key-value pairs.
 * 
 * @see WritableVolatileMapProperty
 * @see WritableMapPropertyImplementation
 */
@ThreadSafe
@Mutable(ReadOnlyMapProperty.class)
public interface WritableMapProperty<KEY, VALUE, READONLY_MAP extends ReadOnlyMap<@Nonnull @Valid("key") KEY, @Nonnull @Valid VALUE>, EXCEPTION extends Exception, OBSERVER extends MapObserver<KEY, VALUE, READONLY_MAP, EXCEPTION, OBSERVER, PROPERTY>, PROPERTY extends ReadOnlyMapProperty<KEY, VALUE, READONLY_MAP, EXCEPTION, OBSERVER, PROPERTY>> extends ReadOnlyMapProperty<KEY, VALUE, READONLY_MAP, EXCEPTION, OBSERVER, PROPERTY> {
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    /**
     * Adds the given value indexed by the given key to this property.
     * 
     * @return {@code true} if the key-value pair was successfully added and {@code false} if the key was already in use.
     */
    @Impure
    public abstract boolean add(@Captured @Nonnull @Valid("key") KEY key, @Captured @Nonnull @Valid VALUE value) throws EXCEPTION, ReentranceException;
    
    /**
     * Removes the value indexed by the given key from this property.
     * 
     * @return the value that was previously associated with the given key or null if the key was not in use.
     */
    @Impure
    public abstract @Capturable @Nullable @Valid VALUE remove(@NonCaptured @Unmodified @Nonnull @Valid("key") KEY key) throws EXCEPTION, ReentranceException;
    
}
