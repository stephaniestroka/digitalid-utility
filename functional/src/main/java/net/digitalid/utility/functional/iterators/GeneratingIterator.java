package net.digitalid.utility.functional.iterators;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.exceptions.IterationExceptionBuilder;
import net.digitalid.utility.functional.failable.FailableProducer;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a generating iterator that generates an infinite number of elements with the given producer.
 */
@Mutable
public class GeneratingIterator<@Specifiable ELEMENT> extends InfiniteIterator<ELEMENT> {
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    protected final @Nonnull FailableProducer<? extends ELEMENT, ?> producer;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected GeneratingIterator(@Captured @Nonnull FailableProducer<? extends ELEMENT, ?> producer) {
        this.producer = producer;
    }
    
    /**
     * Returns a new generating iterator that generates an infinite number of elements with the given producer.
     */
    @Pure
    public static @Capturable <@Specifiable ELEMENT> @Nonnull GeneratingIterator<ELEMENT> with(@Captured @Nonnull FailableProducer<? extends ELEMENT, ?> producer) {
        return new GeneratingIterator<>(producer);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Impure
    @Override
    public @Capturable ELEMENT next() {
        try {
            return producer.produce();
        } catch (@Nonnull Exception exception) {
            throw IterationExceptionBuilder.withCause(exception).build();
        }
    }
    
}
