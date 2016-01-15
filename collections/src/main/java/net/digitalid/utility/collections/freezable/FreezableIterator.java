package net.digitalid.utility.collections.freezable;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.collections.readonly.ReadOnlyIterator;
import net.digitalid.utility.freezable.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.validation.state.Immutable;

/**
 * This interface models an {@link Iterator iterator} that can be {@link Freezable frozen}.
 * As a consequence, all modifying methods may fail with an {@link AssertionError}.
 * (Please note that only the underlying iterable and not the iterator itself is freezable.)
 * <p>
 * <em>Important:</em> Only use freezable or immutable types for the elements!
 * (The type is not restricted to {@link Freezable} or {@link Immutable} so that library types can also be used.)
 * 
 * @see FreezableIterableIterator
 * @see FreezableArrayIterator
 */
public interface FreezableIterator<E> extends ReadOnlyIterator<E>, Freezable {
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    @Override
    public @Nonnull @Frozen ReadOnlyIterator<E> freeze();
    
}
