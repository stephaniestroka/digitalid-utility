package net.digitalid.utility.functional.iterators;

import net.digitalid.utility.functional.interfaces.UnaryOperator;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This class implements an iterating iterator that iterates over the sequence produced by the given operator from the given seed.
 */
public class IteratingIterator<E> implements InfiniteIterator<E> {
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    protected final UnaryOperator<E> operator;
    
    protected final E seed;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected IteratingIterator(UnaryOperator<E> operator, E seed) {
        this.operator = operator;
        this.seed = seed;
        this.lastElement = seed;
    }
    
    /**
     * Returns a new iterating iterator that iterates over the sequence produced by the given operator from the given seed.
     */
    @Pure
    public static <E> IteratingIterator<E> with(UnaryOperator<E> operator, E seed) {
        return new IteratingIterator<>(operator, seed);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    private E lastElement;
    
    @Override
    public E next() {
        lastElement = operator.evaluate(lastElement);
        return lastElement;
    }
    
}
