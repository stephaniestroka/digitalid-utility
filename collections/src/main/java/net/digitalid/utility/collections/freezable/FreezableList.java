package net.digitalid.utility.collections.freezable;

import java.util.List;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.index.Index;
import net.digitalid.utility.validation.annotations.index.IndexForInsertion;
import net.digitalid.utility.collections.readonly.ReadOnlyList;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.annotations.method.Pure;

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
    public @Nonnull FreezableListIterator<E> listIterator(@IndexForInsertion int index);
    
    @Pure
    @Override
    public @Nonnull FreezableList<E> subList(@Index int fromIndex, @IndexForInsertion int toIndex);
    
}
