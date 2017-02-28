package net.digitalid.utility.collections.iterable;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.collections.array.FreezableArray;
import net.digitalid.utility.collections.array.ReadOnlyArray;
import net.digitalid.utility.collections.collection.ReadOnlyCollection;
import net.digitalid.utility.collections.list.FreezableLinkedList;
import net.digitalid.utility.collections.list.FreezableList;
import net.digitalid.utility.collections.map.FreezableLinkedHashMapBuilder;
import net.digitalid.utility.collections.map.FreezableMap;
import net.digitalid.utility.collections.set.FreezableLinkedHashSet;
import net.digitalid.utility.collections.set.FreezableSet;
import net.digitalid.utility.freezable.ReadOnlyInterface;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.functional.failable.FailableUnaryFunction;
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
public interface ReadOnlyIterable<@Specifiable ELEMENT> extends CollectionIterable<ELEMENT>, ReadOnlyInterface {
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableIterable<ELEMENT> clone();
    
    /* -------------------------------------------------- Exports -------------------------------------------------- */
    
    /**
     * Returns the elements of this collection as a freezable array.
     */
    @Pure
    public default @Capturable @Nonnull @NonFrozen FreezableArray<ELEMENT> toFreezableArray() {
        return FreezableArray.withElementsOf(this);
    }
    
    /**
     * Returns the elements of this collection as a freezable list.
     */
    @Pure
    public default @Capturable @Nonnull @NonFrozen FreezableList<ELEMENT> toFreezableList() {
        return FreezableLinkedList.withElementsOf(this);
    }
    
    /**
     * Returns the elements of this collection as a freezable set.
     */
    @Pure
    public default @Capturable @Nonnull @NonFrozen FreezableSet<ELEMENT> toFreezableSet() {
        return FreezableLinkedHashSet.withElementsOf(this);
    }
    
    /**
     * Returns the elements of this iterable as a freezable map with their key determined by the given function.
     */
    @Pure
    public default @Capturable <@Specifiable KEY, @Unspecifiable EXCEPTION extends Exception> @Nonnull @NonFrozen FreezableMap<KEY, ELEMENT> toFreezableMap(@Nonnull FailableUnaryFunction<? super ELEMENT, ? extends KEY, ? extends EXCEPTION> function) throws EXCEPTION {
        final @Nonnull FreezableMap<KEY, ELEMENT> result = FreezableLinkedHashMapBuilder.build();
        for (ELEMENT element : this) {
            result.put(function.evaluate(element), element);
        }
        return result;
    }
    
}
