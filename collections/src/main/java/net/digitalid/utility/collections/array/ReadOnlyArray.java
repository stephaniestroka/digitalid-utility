package net.digitalid.utility.collections.array;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.collections.iterable.ReadOnlyIterable;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.functional.iterators.ReadOnlyArrayIterator;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This interface provides read-only access to arrays and should <em>never</em> be cast away.
 * It is recommended to use only {@link Freezable} or {@link Immutable} types for the elements.
 */
@ReadOnly(FreezableArray.class)
public interface ReadOnlyArray<E> extends ReadOnlyIterable<E> {
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull ReadOnlyArrayIterator<E> iterator();
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableArray<E> clone();
    
}
