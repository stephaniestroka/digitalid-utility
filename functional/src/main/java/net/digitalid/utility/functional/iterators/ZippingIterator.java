package net.digitalid.utility.functional.iterators;

import java.util.Iterator;

import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This class implements a zipping iterator that iterates over the elements of the given iterators in pairs, extending the shorter or truncating the longer iterator depending on the given flag.
 */
public class ZippingIterator<I0, I1> extends DoubleIteratorBasedIterator<Pair<I0, I1>, I0, I1> {
    
    /* -------------------------------------------------- Shortest -------------------------------------------------- */
    
    protected final boolean shortest;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ZippingIterator(Iterator<? extends I0> primaryIterator, Iterator<? extends I1> secondaryIterator, boolean shortest) {
        super(primaryIterator, secondaryIterator);
        
        this.shortest = shortest;
    }
    
    /**
     * Returns a new zipping iterator that iterates over the elements of the given iterators in pairs, extending the shorter or truncating the longer iterator depending on the given flag.
     */
    @Pure
    public static <I0, I1> ZippingIterator<I0, I1> with(Iterator<? extends I0> primaryIterator, Iterator<? extends I1> secondaryIterator, boolean shortest) {
        return new ZippingIterator<>(primaryIterator, secondaryIterator, shortest);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean hasNext() {
        if (shortest) { return primaryIterator.hasNext() && secondaryIterator.hasNext(); }
        else { return primaryIterator.hasNext() || secondaryIterator.hasNext(); }
    }
    
    @Override
    public Pair<I0, I1> next() {
        if (shortest) { return Pair.of(primaryIterator.next(), secondaryIterator.next()); }
        else { return Pair.of(primaryIterator.hasNext() || !secondaryIterator.hasNext() ? primaryIterator.next() : null, secondaryIterator.hasNext() || !primaryIterator.hasNext() ? secondaryIterator.next() : null); }
    }
    
}
