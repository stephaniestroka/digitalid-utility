package net.digitalid.utility.functional.iterators;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.reference.Capturable;
import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.tuples.Pair;

/**
 * This class implements a zipping iterator that iterates over the elements of the given iterators in pairs, extending the shorter or truncating the longer iterator depending on the given flag.
 */
@Mutable
public class ZippingIterator<I0, I1> extends DoubleIteratorBasedIterator<@Nonnull Pair<I0, I1>, I0, I1> {
    
    /* -------------------------------------------------- Shortest -------------------------------------------------- */
    
    protected final boolean shortest;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ZippingIterator(@Nonnull Iterator<? extends I0> primaryIterator, @Nonnull Iterator<? extends I1> secondaryIterator, boolean shortest) {
        super(primaryIterator, secondaryIterator);
        
        this.shortest = shortest;
    }
    
    /**
     * Returns a new zipping iterator that iterates over the elements of the given iterators in pairs, extending the shorter or truncating the longer iterator depending on the given flag.
     */
    @Pure
    public static <I0, I1> @Capturable @Nonnull ZippingIterator<I0, I1> with(@Nonnull Iterator<? extends I0> primaryIterator, @Nonnull Iterator<? extends I1> secondaryIterator, boolean shortest) {
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
    public @Nonnull Pair<I0, I1> next() {
        if (shortest) { return Pair.of(primaryIterator.next(), secondaryIterator.next()); }
        else { return Pair.of(primaryIterator.hasNext() || !secondaryIterator.hasNext() ? primaryIterator.next() : null, secondaryIterator.hasNext() || !primaryIterator.hasNext() ? secondaryIterator.next() : null); }
    }
    
}
