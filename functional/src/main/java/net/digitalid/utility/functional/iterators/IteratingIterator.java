package net.digitalid.utility.functional.iterators;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.exceptions.IterationExceptionBuilder;
import net.digitalid.utility.functional.failable.FailableUnaryOperator;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements an iterating iterator that iterates over the sequence produced by the given operator from the given first element.
 */
@Mutable
public class IteratingIterator<@Specifiable ELEMENT> extends InfiniteIterator<ELEMENT> {
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    private ELEMENT nextElement;
    
    protected final @Nonnull FailableUnaryOperator<ELEMENT, ?> unaryOperator;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected IteratingIterator(@Captured ELEMENT firstElement, @Nonnull FailableUnaryOperator<ELEMENT, ?> unaryOperator) {
        this.nextElement = firstElement;
        this.unaryOperator = unaryOperator;
    }
    
    /**
     * Returns a new iterating iterator that iterates over the sequence produced by the given operator from the given first element.
     */
    @Pure
    public static @Capturable <@Specifiable ELEMENT> @Nonnull IteratingIterator<ELEMENT> with(@Captured ELEMENT firstElement, @Nonnull FailableUnaryOperator<ELEMENT, ?> unaryOperator) {
        return new IteratingIterator<>(firstElement, unaryOperator);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Impure
    @Override
    public ELEMENT next() {
        try {
            final ELEMENT lastElement = nextElement;
            this.nextElement = unaryOperator.evaluate(lastElement);
            return lastElement;
        } catch (@Nonnull Exception exception) {
            throw IterationExceptionBuilder.withCause(exception).build();
        }
    }
    
}
