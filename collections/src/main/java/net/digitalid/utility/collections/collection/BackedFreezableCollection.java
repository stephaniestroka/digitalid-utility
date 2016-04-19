package net.digitalid.utility.collections.collection;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Referenced;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.collections.list.BackedFreezableList;
import net.digitalid.utility.collections.list.FreezableArrayList;
import net.digitalid.utility.collections.set.BackedFreezableSet;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.functional.iterators.ReadOnlyIterableIterator;
import net.digitalid.utility.functional.iterators.ReadOnlyIterator;
import net.digitalid.utility.generator.annotations.GenerateNoBuilder;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class implements a {@link Collection collection} that can be {@link FreezableInterface frozen}.
 * It is recommended to use only {@link ReadOnly} or {@link Immutable} types for the elements.
 * The implementation is backed by an ordinary {@link Collection collection}. 
 * 
 * @see BackedFreezableList
 * @see BackedFreezableSet
 */
@GenerateNoBuilder
@Freezable(ReadOnlyCollection.class)
public class BackedFreezableCollection<E> extends RootClass implements FreezableCollection<E> {
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    /**
     * Stores a reference to the underlying freezable.
     */
    protected final @Referenced @Nonnull FreezableInterface freezable;
    
    /**
     * Stores the underlying collection.
     */
    private final @Nonnull Collection<E> collection;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    protected BackedFreezableCollection(@Nonnull FreezableInterface freezable, @Nonnull Collection<E> collection) {
        this.freezable = Objects.requireNonNull(freezable);
        this.collection = Objects.requireNonNull(collection);
    }
    
    /**
     * Returns a new freezable collection backed by the given freezable and collection.
     */
    @Pure
    public static <E> @Capturable @Nonnull BackedFreezableCollection<E> with(@Referenced @Modified @Nonnull FreezableInterface freezable, @Captured @Nonnull Collection<E> collection) {
        return new BackedFreezableCollection<>(freezable, collection);
    }
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean isFrozen() {
        return freezable.isFrozen();
    }
    
    @Impure
    @Override
    public @Nonnull @Frozen ReadOnlyCollection<E> freeze() {
        freezable.freeze();
        return this;
    }
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableCollection<E> clone() {
        return FreezableArrayList.with(collection);
    }
    
    /* -------------------------------------------------- Collection -------------------------------------------------- */
    
    @Pure
    @Override
    public int size() {
        return collection.size();
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull ReadOnlyIterator<E> iterator() {
        return ReadOnlyIterableIterator.with(collection.iterator());
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    @Impure
    @Override
    @NonFrozenRecipient
    public boolean add(@Captured E element) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return collection.add(element);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public boolean addAll(@NonCaptured @Unmodified @Nonnull Collection<? extends E> c) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return collection.addAll(c);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public boolean remove(@NonCaptured @Unmodified @Nullable Object object) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return collection.remove(object);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public boolean removeAll(@NonCaptured @Unmodified @Nonnull Collection<?> c) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return collection.removeAll(c);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public boolean retainAll(@NonCaptured @Unmodified @Nonnull Collection<?> c) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return collection.retainAll(c);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public void clear() {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        collection.clear();
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean equals(@Nullable Object object) {
        return collection.equals(object);
    }
    
    @Pure
    @Override
    public int hashCode() {
        return collection.hashCode();
    }
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return collection.toString();
    }
    
}
