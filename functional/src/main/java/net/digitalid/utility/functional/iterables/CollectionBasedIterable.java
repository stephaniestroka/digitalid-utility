package net.digitalid.utility.functional.iterables;

import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.parameter.Referenced;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.functional.iterators.SingleIteratorBasedIterator;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.Updating;

/**
 * This class implements the collection iterable interface based on a collection.
 */
@Updating
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
    public @Capturable @Nonnull Iterator<E> iterator() {
        return new SingleIteratorBasedIterator<E, E>(collection.iterator()) {
            
            @Pure
            @Override
            public boolean hasNext() {
                return primaryIterator.hasNext();
            }
            
            @Impure
            @Override
            public @NonCapturable E next() {
                return primaryIterator.next();
            }
            
        };
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonNegative int size() {
        return collection.size();
    }
    
}
