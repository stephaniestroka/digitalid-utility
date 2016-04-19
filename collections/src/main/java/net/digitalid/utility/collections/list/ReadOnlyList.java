package net.digitalid.utility.collections.list;

import java.util.List;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.collections.collection.ReadOnlyCollection;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.functional.iterators.ReadOnlyListIterator;
import net.digitalid.utility.generator.annotations.GenerateNoBuilder;
import net.digitalid.utility.generator.annotations.GenerateNoSubclass;
import net.digitalid.utility.validation.annotations.index.Index;
import net.digitalid.utility.validation.annotations.index.IndexForInsertion;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This interface provides read-only access to {@link List lists} and should <em>never</em> be cast away (unless external code requires it).
 * It is recommended to use only {@link ReadOnly} or {@link Immutable} types for the elements.
 */
@GenerateNoBuilder
@GenerateNoSubclass
@ReadOnly(FreezableList.class)
public interface ReadOnlyList<E> extends ReadOnlyCollection<E> {
    
    /* -------------------------------------------------- List -------------------------------------------------- */
    
    /**
     * @see List#listIterator()
     */
    @Pure
    public @Capturable @Nonnull ReadOnlyListIterator<E> listIterator();
    
    /**
     * @see List#listIterator(int)
     */
    @Pure
    public @Capturable @Nonnull ReadOnlyListIterator<E> listIterator(@IndexForInsertion int index);
    
    /**
     * @see List#subList(int, int)
     */
    @Pure
    public @NonCapturable @Nonnull ReadOnlyList<E> subList(@Index int fromIndex, @IndexForInsertion int toIndex);
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableList<E> clone();
    
}
