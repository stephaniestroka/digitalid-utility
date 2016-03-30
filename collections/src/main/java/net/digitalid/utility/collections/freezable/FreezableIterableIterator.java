package net.digitalid.utility.collections.freezable;

import java.util.Iterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.collections.readonly.ReadOnlyIterator;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.reference.Capturable;
import net.digitalid.utility.annotations.reference.Captured;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This interface models an {@link Iterator iterator} that can be {@link Freezable frozen}.
 * As a consequence, all modifying methods may fail with an {@link AssertionError}.
 * (Please note that only the underlying iterable and not the iterator itself is freezable.)
 * <p>
 * <em>Important:</em> Only use freezable or immutable types for the elements!
 * (The type is not restricted to {@link Freezable} or {@link Immutable} so that library types can also be used.)
 * 
 * @see FreezableListIterator
 */
class FreezableIterableIterator<E> implements FreezableIterator<E> {
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    /**
     * Stores a reference to the underlying iterable.
     */
    protected final @Nonnull FreezableIterable<E> iterable;
    
    /**
     * Stores a reference to the underlying iterator.
     */
    private final @Nonnull Iterator<E> iterator;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new freezable iterator.
     * 
     * @param iterable a reference to the underlying iterable.
     * @param iterator a reference to the underlying iterator.
     */
    protected FreezableIterableIterator(@Nonnull FreezableIterable<E> iterable, @Captured @Nonnull Iterator<E> iterator) {
        this.iterable = iterable;
        this.iterator = iterator;
    }
    
    /**
     * Creates a new freezable iterator.
     * 
     * @param iterable a reference to the underlying iterable.
     * @param iterator a reference to the underlying iterator.
     * 
     * @return a new freezable iterator.
     */
    @Pure
    static @Capturable @Nonnull <E> FreezableIterableIterator<E> get(@Nonnull FreezableIterable<E> iterable, @Captured @Nonnull Iterator<E> iterator) {
        return new FreezableIterableIterator<>(iterable, iterator);
    }
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    @Pure
    @Override
    public final boolean isFrozen() {
        return iterable.isFrozen();
    }
    
    @Override
    public @Nonnull @Frozen ReadOnlyIterator<E> freeze() {
        iterable.freeze();
        return this;
    }
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }
    
    @Override
    public @Nullable E next() {
        return iterator.next();
    }
    
    /* -------------------------------------------------- Operation -------------------------------------------------- */
    
    @Override
    @NonFrozenRecipient
    public void remove() {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        iterator.remove();
    }
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableIterator<E> clone() {
        return iterable.clone().iterator();
    }
    
}
