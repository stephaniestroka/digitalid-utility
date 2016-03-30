package net.digitalid.utility.functional.iterators;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.functional.interfaces.Producer;

/**
 * This class implements a generating iterator that generates an infinite number of elements with the given producer.
 */
@Mutable
public class GeneratingIterator<E> implements InfiniteIterator<E> {
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    protected final @Nonnull Producer<? extends E> producer;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected GeneratingIterator(@Nonnull Producer<? extends E> producer) {
        this.producer = producer;
    }
    
    /**
     * Returns a new generating iterator that generates an infinite number of elements with the given producer.
     */
    @Pure
    public static <E> @Capturable @Nonnull GeneratingIterator<E> with(@Nonnull Producer<? extends E> producer) {
        return new GeneratingIterator<>(producer);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Override
    public E next() {
        return producer.produce();
    }
    
}
