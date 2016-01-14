package net.digitalid.utility.collections.freezable;

import java.util.Collection;
import javax.annotation.Nonnull;
import net.digitalid.utility.validation.state.Immutable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.collections.readonly.ReadOnlyCollection;

/**
 * This interface models a {@link Collection collection} that can be {@link Freezable frozen}.
 * As a consequence, all modifying methods may fail with an {@link AssertionError}.
 * <p>
 * <em>Important:</em> Only use freezable or immutable types for the elements!
 * (The type is not restricted to {@link Freezable} or {@link Immutable} so that library types can also be used.)
 * 
 * @see FreezableList
 * @see FreezableSet
 */
public interface FreezableCollection<E> extends ReadOnlyCollection<E>, Collection<E>, FreezableIterable<E> {
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    @Override
    public @Nonnull @Frozen ReadOnlyCollection<E> freeze();
    
}
