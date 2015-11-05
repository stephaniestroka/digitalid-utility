package net.digitalid.utility.collections.freezable;

import java.util.List;
import javax.annotation.Nonnull;
import net.digitalid.utility.annotations.state.Immutable;
import net.digitalid.utility.annotations.state.Pure;
import net.digitalid.utility.collections.annotations.freezable.Frozen;
import net.digitalid.utility.collections.annotations.index.ValidIndex;
import net.digitalid.utility.collections.annotations.index.ValidIndexForInsertion;
import net.digitalid.utility.collections.readonly.ReadOnlyList;

/**
 * This interface models a {@link List list} that can be {@link Freezable frozen}.
 * As a consequence, all modifying methods may fail with an {@link AssertionError}.
 * <p>
 * <em>Important:</em> Only use freezable or immutable types for the elements!
 * (The type is not restricted to {@link Freezable} or {@link Immutable} so that library types can also be used.)
 * 
 * @see FreezableArrayList
 * @see FreezableLinkedList
 * @see BackedFreezableList
 */
public interface FreezableList<E> extends ReadOnlyList<E>, List<E>, FreezableCollection<E> {
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    @Override
    public @Nonnull @Frozen ReadOnlyList<E> freeze();
    
    /* -------------------------------------------------- List -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull FreezableListIterator<E> listIterator();
    
    @Pure
    @Override
    public @Nonnull FreezableListIterator<E> listIterator(@ValidIndexForInsertion int index);
    
    @Pure
    @Override
    public @Nonnull FreezableList<E> subList(@ValidIndex int fromIndex, @ValidIndexForInsertion int toIndex);
    
}
