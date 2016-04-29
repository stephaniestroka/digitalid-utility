package net.digitalid.utility.collections.list;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Referenced;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.collections.collection.BackedFreezableCollection;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.functional.fixes.Brackets;
import net.digitalid.utility.functional.iterators.ReadOnlyListIterator;
import net.digitalid.utility.validation.annotations.index.Index;
import net.digitalid.utility.validation.annotations.index.IndexForInsertion;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This class implements a {@link Set set} that can be {@link FreezableInterface frozen}.
 * It is recommended to use only {@link ReadOnly} or {@link Immutable} types for the elements.
 * The implementation is backed by an ordinary {@link List list}. 
 */
@Freezable(ReadOnlyList.class)
public class BackedFreezableList<E> extends BackedFreezableCollection<E> implements FreezableList<E> {
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    /**
     * Stores the underlying list.
     */
    private final @Nonnull List<E> list;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected BackedFreezableList(@Nonnull FreezableInterface freezable, @Nonnull List<E> list) {
        super(freezable, list);
        
        this.list = Objects.requireNonNull(list);
    }
    
    /**
     * Returns a new freezable list backed by the given freezable and list.
     */
    @Pure
    public static @Capturable <E> @Nonnull BackedFreezableList<E> with(@Referenced @Modified @Nonnull FreezableInterface freezable, @Captured @Nonnull List<E> list) {
        return new BackedFreezableList<>(freezable, list);
    }
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    @Impure
    @Override
    public @Nonnull @Frozen ReadOnlyList<E> freeze() {
        super.freeze();
        return this;
    }
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableList<E> clone() {
        return FreezableArrayList.with(list);
    }
    
    /* -------------------------------------------------- List -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonCapturable E get(@Index int index) {
        return list.get(index);
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull ReadOnlyListIterator<E> listIterator() {
        return ReadOnlyListIterator.with(list.listIterator());
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull ReadOnlyListIterator<E> listIterator(@IndexForInsertion int index) {
        return ReadOnlyListIterator.with(list.listIterator(index));
    }
    
    @Pure
    @Override
    public @NonCapturable @Nonnull FreezableList<E> subList(@Index int fromIndex, @IndexForInsertion int toIndex) {
        return BackedFreezableList.with(this, list.subList(fromIndex, toIndex));
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    @Impure
    @Override
    @NonFrozenRecipient
    public @Capturable E set(@Index int index, @Captured E element) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return list.set(index, element);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public void add(@IndexForInsertion int index, @Captured E element) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        list.add(index, element);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public @Capturable E remove(@Index int index) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return list.remove(index);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public boolean addAll(@IndexForInsertion int index, @NonCaptured @Unmodified @Nonnull Collection<? extends E> collection) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return list.addAll(index, collection);
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return join(Brackets.SQUARE);
    }
    
}
