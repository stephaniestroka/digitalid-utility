package net.digitalid.utility.collections.readonly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.index.Index;
import net.digitalid.utility.collections.freezable.FreezableArray;
import net.digitalid.utility.collections.freezable.FreezableList;
import net.digitalid.utility.freezable.Freezable;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.validation.annotations.reference.Capturable;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This interface provides read-only access to arrays and should <em>never</em> be cast away.
 * <p>
 * <em>Important:</em> Only use freezable or immutable types for the elements!
 * (The type is not restricted to {@link Freezable} or {@link Immutable} so that library types can also be used.)
 * 
 * @see FreezableArray
 */
public interface ReadOnlyArray<E> extends ReadOnlyIterable<E> {
    
    /* -------------------------------------------------- Retrieval -------------------------------------------------- */
    
    /**
     * Returns the size of this array.
     * 
     * @return the size of this array.
     */
    @Pure
    public int size();
    
    /**
     * Returns the element at the given index.
     * 
     * @param index the index of the element to be returned.
     * 
     * @return the element at the given index.
     */
    @Pure
    public @Nullable E getNullable(@Index int index);
    
    /**
     * Returns whether the element at the given index is null.
     * 
     * @param index the index of the element to be checked.
     * 
     * @return whether the element at the given index is null.
     */
    @Pure
    public boolean isNull(@Index int index);
    
    /**
     * Returns the element at the given index.
     * 
     * @param index the index of the element to be returned.
     * 
     * @return the element at the given index.
     * 
     * @require !isNull(index) : "The element at the given index is not null.";
     */
    @Pure
    public @Nonnull E getNonNullable(@Index int index);
    
    @Pure
    @Override
    public @Nonnull ReadOnlyArrayIterator<E> iterator();
    
    /* -------------------------------------------------- Conditions -------------------------------------------------- */
    
    /**
     * Returns whether this array contains an element which is null.
     * If it does not, {@link #getNullable(int)} is guaranteed to return not null for every valid index.
     * 
     * @return {@code true} if this array contains null, {@code false} otherwise.
     */
    @Pure
    public boolean containsNull();
    
    /**
     * Returns whether this array contains duplicates (including null values).
     * 
     * @return {@code true} if this array contains duplicates, {@code false} otherwise.
     */
    @Pure
    public boolean containsDuplicates();
    
    /* -------------------------------------------------- Conversions -------------------------------------------------- */
    
    /**
     * Returns the elements of this array as an array.
     * 
     * @return the elements of this array as an array.
     */
    @Pure
    public @Capturable @Nonnull E[] toArray();
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableArray<E> clone();
    
    /**
     * Returns the elements of this array in a freezable list.
     * 
     * @return the elements of this array in a freezable list.
     */
    @Pure
    public @Capturable @Nonnull @NonFrozen FreezableList<E> toFreezableList();
    
}
