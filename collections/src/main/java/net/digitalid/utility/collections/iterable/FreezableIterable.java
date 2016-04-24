package net.digitalid.utility.collections.iterable;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.collections.collection.FreezableCollection;
import net.digitalid.utility.contracts.exceptions.PreconditionViolationException;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This interface models an {@link Iterable iterable} that can be {@link FreezableInterface frozen}.
 * As a consequence, all modifying methods may fail with a {@link PreconditionViolationException}.
 * It is recommended to use only {@link ReadOnly} or {@link Immutable} types for the elements.
 * 
 * @see FreezableCollection
 * @see FreezableArray
 */
@Freezable(ReadOnlyIterable.class)
public interface FreezableIterable<E> extends ReadOnlyIterable<E>, FreezableInterface {
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    @Impure
    @Override
    public @Chainable @Nonnull @Frozen ReadOnlyIterable<E> freeze();
    
}
