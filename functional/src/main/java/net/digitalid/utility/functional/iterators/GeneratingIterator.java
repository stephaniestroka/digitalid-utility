package net.digitalid.utility.functional.iterators;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.exceptions.FailedIterationException;
import net.digitalid.utility.functional.failable.FailableProducer;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a generating iterator that generates an infinite number of elements with the given producer.
 */
@Mutable
public class GeneratingIterator<E> extends InfiniteIterator<E> {
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    protected final @Nonnull FailableProducer<? extends E, ?> producer;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected GeneratingIterator(@Captured @Nonnull FailableProducer<? extends E, ?> producer) {
        this.producer = producer;
    }
    
    /**
     * Returns a new generating iterator that generates an infinite number of elements with the given producer.
     */
    @Pure
    public static <E> @Capturable @Nonnull GeneratingIterator<E> with(@Captured @Nonnull FailableProducer<? extends E, ?> producer) {
        return new GeneratingIterator<>(producer);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Impure
    @Override
    public @Capturable E next() {
        try {
            return producer.produce();
        } catch (@Nonnull Exception exception) {
            throw FailedIterationException.with(exception);
        }
    }
    
}
