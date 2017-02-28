package net.digitalid.utility.functional.iterators;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements an iterator which is based on a single iterator.
 * 
 * @see DoubleIteratorBasedIterator
 */
@Mutable
public abstract class SingleIteratorBasedIterator<@Specifiable OUTPUT, @Specifiable INPUT0> extends ReadOnlyIterator<OUTPUT> {
    
    /* -------------------------------------------------- Primary Iterator -------------------------------------------------- */
    
    /**
     * Stores the primary iterator on which this iterator is based.
     */
    protected final @Nonnull Iterator<? extends INPUT0> primaryIterator;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected SingleIteratorBasedIterator(@Captured @Nonnull Iterator<? extends INPUT0> primaryIterator) {
        this.primaryIterator = primaryIterator;
    }
    
}
