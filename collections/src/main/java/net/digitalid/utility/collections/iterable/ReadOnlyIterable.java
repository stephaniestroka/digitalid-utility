package net.digitalid.utility.collections.iterable;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.collections.array.FreezableArray;
import net.digitalid.utility.collections.list.FreezableLinkedList;
import net.digitalid.utility.collections.list.FreezableList;
import net.digitalid.utility.collections.map.FreezableLinkedHashMap;
import net.digitalid.utility.collections.map.FreezableMap;
import net.digitalid.utility.collections.set.FreezableLinkedHashSet;
import net.digitalid.utility.collections.set.FreezableSet;
import net.digitalid.utility.freezable.ReadOnlyInterface;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.functional.interfaces.UnaryFunction;
import net.digitalid.utility.functional.iterables.CollectionIterable;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This interface provides read-only access to {@link Iterable iterables} and should <em>never</em> be cast away.
 * It is recommended to use only {@link ReadOnly} or {@link Immutable} types for the elements.
 * 
 * @see ReadOnlyCollection
 * @see ReadOnlyArray
 */
@ReadOnly(FreezableIterable.class)
public interface ReadOnlyIterable<E> extends CollectionIterable<E>, ReadOnlyInterface {
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableIterable<E> clone();
    
    /* -------------------------------------------------- Exports -------------------------------------------------- */
    
    /**
     * Returns the elements of this collection as a freezable array.
     */
    @Pure
    public default @Capturable @Nonnull @NonFrozen FreezableArray<E> toFreezableArray() {
        return FreezableArray.with(this);
    }
    
    /**
     * Returns the elements of this collection as a freezable list.
     */
    @Pure
    public default @Capturable @Nonnull @NonFrozen FreezableList<E> toFreezableList() {
        return FreezableLinkedList.with(this);
    }
    
    /**
     * Returns the elements of this collection as a freezable set.
     */
    @Pure
    public default @Capturable @Nonnull @NonFrozen FreezableSet<E> toFreezableSet() {
        return FreezableLinkedHashSet.with(this);
    }
    
    /**
     * Returns the elements of this iterable as a freezable map with their key determined by the given function.
     */
    @Pure
    public default <K> @Capturable @Nonnull @NonFrozen FreezableMap<K, E> toFreezableMap(@Nonnull UnaryFunction<? super E, ? extends K> function) {
        final @Nonnull FreezableMap<K, E> result = FreezableLinkedHashMap.withDefaultCapacity();
        for (E element : this) {
            result.put(function.evaluate(element), element);
        }
        return result;
    }
    
}
