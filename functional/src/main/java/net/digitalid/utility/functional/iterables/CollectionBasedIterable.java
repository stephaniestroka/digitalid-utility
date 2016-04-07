package net.digitalid.utility.functional.iterables;

import java.util.Collection;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.parameter.Referenced;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.functional.iterators.ReadOnlyIterableIterator;
import net.digitalid.utility.functional.iterators.ReadOnlyIterator;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This class implements the collection iterable interface based on a collection.
 */
@ReadOnly
public class CollectionBasedIterable<E> implements CollectionIterable<E> {
    
    /* -------------------------------------------------- Collection -------------------------------------------------- */
    
    private final @Referenced @Nonnull Collection<? extends E> collection;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected CollectionBasedIterable(@Referenced @Unmodified @Nonnull Collection<? extends E> collection) {
        this.collection = collection;
    }
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull ReadOnlyIterator<E> iterator() {
        return ReadOnlyIterableIterator.with(collection.iterator());
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonNegative int size() {
        return collection.size();
    }
    
}
