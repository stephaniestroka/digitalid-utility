package net.digitalid.utility.collections.readonly;

import javax.annotation.Nonnull;

import net.digitalid.utility.collections.freezable.FreezableIterable;
import net.digitalid.utility.freezable.Freezable;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.freezable.ReadOnly;
import net.digitalid.utility.validation.annotations.reference.Capturable;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This interface provides read-only access to {@link Iterable iterables} and should not be lost by assigning its objects to a supertype.
 * <p>
 * <em>Important:</em> Only use freezable or immutable types for the elements!
 * (The type is not restricted to {@link Freezable} or {@link Immutable} so that library types can also be used.)
 * 
 * @see FreezableIterable
 */
public interface ReadOnlyIterable<E> extends Iterable<E>, ReadOnly {
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull ReadOnlyIterator<E> iterator();
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableIterable<E> clone();
    
}
