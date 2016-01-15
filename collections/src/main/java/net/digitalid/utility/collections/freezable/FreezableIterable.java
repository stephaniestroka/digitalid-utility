package net.digitalid.utility.collections.freezable;

import javax.annotation.Nonnull;

import net.digitalid.utility.collections.readonly.ReadOnlyIterable;
import net.digitalid.utility.freezable.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.validation.state.Immutable;
import net.digitalid.utility.validation.state.Pure;

/**
 * This interface models an {@link Iterable iterable} that can be {@link Freezable frozen}.
 * As a consequence, all modifying methods may fail with an {@link AssertionError}.
 * <p>
 * <em>Important:</em> Only use freezable or immutable types for the elements!
 * (The type is not restricted to {@link Freezable} or {@link Immutable} so that library types can also be used.)
 * 
 * @see FreezableCollection
 * @see FreezableArray
 */
public interface FreezableIterable<E> extends ReadOnlyIterable<E>, Freezable {
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    @Override
    public @Nonnull @Frozen ReadOnlyIterable<E> freeze();
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull FreezableIterator<E> iterator();
    
}
