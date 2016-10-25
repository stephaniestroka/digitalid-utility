package net.digitalid.utility.collections.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.Shared;
import net.digitalid.utility.collections.collection.FreezableCollection;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class models an iterator which is backed by a freezable collection.
 */
@Mutable
@GenerateSubclass
public abstract class FreezableIterator<E> implements Iterator<E> {
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Pure
    abstract @Nonnull Iterator<E> getIterator();
    
    /* -------------------------------------------------- Collection -------------------------------------------------- */
    
    @Pure
    abstract @Nonnull FreezableCollection<E> getCollection();
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Returns a new freezable iterator backed by the given iterator and freezable collection.
     */
    @Pure
    public static <E> @Nonnull FreezableIterator<E> with(@Captured @Nonnull Iterator<E> iterator, @Shared @Nonnull FreezableCollection<E> collection) {
        return new FreezableIteratorSubclass<E>(iterator, collection);
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean hasNext() {
        return getIterator().hasNext();
    }
    
    @Impure
    @Override
    public E next() throws NoSuchElementException {
        return getIterator().next();
    }
    
    @Impure
    @Override
    public void remove() throws IllegalStateException {
        Require.that(!getCollection().isFrozen()).orThrow("The underlying collection may not be frozen.");
        
        getIterator().remove();
    }
    
}
