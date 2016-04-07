package net.digitalid.utility.functional.iterators;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.type.Mutable;

/**
 * This class implements an iterator which is based on a single iterator.
 * 
 * @see DoubleIteratorBasedIterator
 */
@Mutable
public abstract class SingleIteratorBasedIterator<O, I0> extends ReadOnlyIterator<O> {
    
    /* -------------------------------------------------- Primary Iterator -------------------------------------------------- */
    
    /**
     * Stores the primary iterator on which this iterator is based.
     */
    protected final @Nonnull Iterator<? extends I0> primaryIterator;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected SingleIteratorBasedIterator(@Captured @Nonnull Iterator<? extends I0> primaryIterator) {
        this.primaryIterator = primaryIterator;
    }
    
}
