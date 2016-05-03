package net.digitalid.utility.functional.iterators;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.interfaces.UnaryOperator;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements an iterating iterator that iterates over the sequence produced by the given operator from the given first element.
 */
@Mutable
public class IteratingIterator<E> extends InfiniteIterator<E> {
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    private E nextElement;
    
    protected final @Nonnull UnaryOperator<E> unaryOperator;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected IteratingIterator(@Captured E firstElement, @Nonnull UnaryOperator<E> unaryOperator) {
        this.nextElement = firstElement;
        this.unaryOperator = unaryOperator;
    }
    
    /**
     * Returns a new iterating iterator that iterates over the sequence produced by the given operator from the given first element.
     */
    @Pure
    public static <E> @Capturable @Nonnull IteratingIterator<E> with(@Captured E firstElement, @Nonnull UnaryOperator<E> unaryOperator) {
        return new IteratingIterator<>(firstElement, unaryOperator);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Impure
    @Override
    public E next() {
        final E lastElement = nextElement;
        this.nextElement = unaryOperator.evaluate(lastElement);
        return lastElement;
    }
    
}
