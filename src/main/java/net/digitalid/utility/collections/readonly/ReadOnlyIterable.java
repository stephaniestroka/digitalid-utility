package net.digitalid.utility.collections.readonly;

import javax.annotation.Nonnull;
import net.digitalid.utility.annotations.reference.Capturable;
import net.digitalid.utility.annotations.state.Immutable;
import net.digitalid.utility.annotations.state.Pure;
import net.digitalid.utility.collections.annotations.freezable.NonFrozen;
import net.digitalid.utility.collections.freezable.Freezable;
import net.digitalid.utility.collections.freezable.FreezableIterable;

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
