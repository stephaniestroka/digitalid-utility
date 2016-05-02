package net.digitalid.utility.functional.iterators;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.interfaces.UnaryOperator;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements an iterating iterator that iterates over the sequence produced by the given operator from the given seed.
 */
@Mutable
public class IteratingIterator<E> extends InfiniteIterator<E> {
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    protected final @Nonnull UnaryOperator<E> operator;
    
    protected final E seed;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected IteratingIterator(@Nonnull UnaryOperator<E> operator, @Captured E seed) {
        this.operator = operator;
        this.seed = seed;
        this.lastElement = seed;
    }
    
    /**
     * Returns a new iterating iterator that iterates over the sequence produced by the given operator from the given seed.
     */
    @Pure
    public static <E> @Capturable @Nonnull IteratingIterator<E> with(@Nonnull UnaryOperator<E> operator, @Captured E seed) {
        return new IteratingIterator<>(operator, seed);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    private E lastElement;
    
    @Impure
    @Override
    public E next() {
        lastElement = operator.evaluate(lastElement);
        return lastElement;
    }
    
}
