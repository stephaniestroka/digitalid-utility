package net.digitalid.utility.collections.freezable;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.collections.readonly.ReadOnlyList;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.freezable.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.functional.fixes.Brackets;
import net.digitalid.utility.functional.fixes.IterableConverter;
import net.digitalid.utility.validation.annotations.index.Index;
import net.digitalid.utility.validation.annotations.index.IndexForInsertion;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class implements a {@link Set set} that can be {@link Freezable frozen}.
 * As a consequence, all modifying methods may fail with an {@link AssertionError}.
 * The implementation is backed by an ordinary {@link List list}. 
 * <p>
 * <em>Important:</em> Only use freezable or immutable types for the elements!
 * (The type is not restricted to {@link Freezable} or {@link Immutable} so that library types can also be used.)
 */
final class BackedFreezableList<E> extends BackedFreezableCollection<E> implements FreezableList<E> {
    
    /* -------------------------------------------------- Field -------------------------------------------------- */
    
    /**
     * Stores a reference to the list.
     */
    private final @Nonnull List<E> list;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new freezable sublist.
     * 
     * @param freezable a reference to the underlying freezable.
     * @param list a reference to the underlying list.
     */
    private BackedFreezableList(@Nonnull Freezable freezable, @Nonnull List<E> list) {
        super(freezable, list);
        
        this.list = list;
    }
    
    /**
     * Creates a new freezable sublist.
     * 
     * @param freezable a reference to the underlying freezable.
     * @param list a reference to the underlying list.
     */
    @Pure
    static @Nonnull <E> BackedFreezableList<E> get(@Nonnull Freezable freezable, @Nonnull List<E> list) {
        return new BackedFreezableList<>(freezable, list);
    }
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    @Override
    public @Nonnull @Frozen ReadOnlyList<E> freeze() {
        super.freeze();
        return this;
    }
    
    /* -------------------------------------------------- List -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nullable E get(@Index int index) {
        return list.get(index);
    }
    
    @Pure
    @Override
    public @Nullable E getNullable(@Index int index) {
        return list.get(index);
    }
    
    @Pure
    @Override
    public boolean isNull(@Index int index) {
        return get(index) == null;
    }
    
    @Pure
    @Override
    @SuppressWarnings("null")
    public @Nonnull E getNonNullable(@Index int index) {
        final @Nullable E element = get(index);
        Require.that(element != null).orThrow("The element at the given index is not null.");
        
        return element;
    }
    
    @Pure
    @Override
    public int indexOf(@Nullable Object object) {
        return list.indexOf(object);
    }
    
    @Pure
    @Override
    public int lastIndexOf(@Nullable Object object) {
        return list.lastIndexOf(object);
    }
    
    @Pure
    @Override
    public @Nonnull FreezableListIterator<E> listIterator() {
        return new FreezableListIterator<>(this, list.listIterator());
    }
    
    @Pure
    @Override
    public @Nonnull FreezableListIterator<E> listIterator(@IndexForInsertion int index) {
        return new FreezableListIterator<>(this, list.listIterator(index));
    }
    
    @Pure
    @Override
    public @Nonnull FreezableList<E> subList(@Index int fromIndex, @IndexForInsertion int toIndex) {
        return new BackedFreezableList<>(this, list.subList(fromIndex, toIndex));
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    @Override
    @NonFrozenRecipient
    public @Nullable E set(@Index int index, @Nullable E element) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return list.set(index, element);
    }
    
    @Override
    @NonFrozenRecipient
    public void add(@IndexForInsertion int index, @Nullable E element) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        list.add(index, element);
    }
    
    @Override
    @NonFrozenRecipient
    public @Nullable E remove(@Index int index) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return list.remove(index);
    }
    
    @Override
    @NonFrozenRecipient
    public boolean addAll(@IndexForInsertion int index, @Nonnull Collection<? extends E> collection) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return list.addAll(index, collection);
    }
    
    /* -------------------------------------------------- Conditions -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean containsNull() {
        for (final @Nullable Object element : this) {
            if (element == null) { return true; }
        }
        return false;
    }
    
    @Pure
    @Override
    public boolean containsDuplicates() {
        final @Nonnull HashSet<E> set = new HashSet<>(size());
        for (final @Nullable E element : this) {
            if (set.contains(element)) { return true; }
            else { set.add(element); }
        }
        return false;
    }
    
    /* -------------------------------------------------- Ordering -------------------------------------------------- */
    
    /**
     * Returns whether the elements in this list are ordered (excluding null values).
     * 
     * @param strictly whether the ordering is strict (i.e. without equal values).
     * @param ascending whether the ordering is ascending (true) or descending (false).
     * 
     * @return {@code true} if the elements in this list are ordered, {@code false} otherwise.
     */
    @Pure
    @SuppressWarnings("unchecked")
    private boolean isOrdered(boolean strictly, boolean ascending) {
        @Nullable E lastElement = null;
        for (final @Nullable E element : this) {
            if (element == null) { continue; }
            if (lastElement != null) {
                if (element instanceof Comparable<?>) {
                    if (((Comparable<E>) element).compareTo(lastElement) * (ascending ? 1 : -1) < (strictly ? 1 : 0)) { return false; }
                }
            }
            lastElement = element;
        }
        return true;
    }
    
    @Pure
    @Override
    public boolean isAscending() {
        return isOrdered(false, true);
    }
    
    @Pure
    @Override
    public boolean isStrictlyAscending() {
        return isOrdered(true, true);
    }
    
    @Pure
    @Override
    public boolean isDescending() {
        return isOrdered(false, false);
    }
    
    @Pure
    @Override
    public boolean isStrictlyDescending() {
        return isOrdered(true, false);
    }
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableList<E> clone() {
        return FreezableArrayList.getNonNullable(list);
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return IterableConverter.toString(this, Brackets.SQUARE);
    }
    
}
